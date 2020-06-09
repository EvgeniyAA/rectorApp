package ru.ikbfu.rectorapp.ui.widgets

import android.content.Context
import android.os.Environment
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.avatar_view.view.*
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.utils.TYPE_FILE
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        initView(context)
    }

    companion object {
        private const val PHOTO_DIR_NAME = "entrant_photo"
    }

    /**
     * set text size if photo empty
     * @param res dimens android
     * */
    fun setSizeText(@DimenRes res: Int) = apply {
        avatar_text.textSize = context.resources.getDimension(res)
    }
    fun enableBorder(enable: Boolean) {
        if (enable) {
            avatar_view.background = ContextCompat.getDrawable(context,R.drawable.avatar_circle)
            val paddingDp = 4
            val density = context.resources.displayMetrics.density
            val padding = (paddingDp * density).toInt()
            avatar_image.setPadding(padding,padding,padding,padding)
        }
        else {
            avatar_view.background = ContextCompat.getDrawable(context, R.color.transparent)
            avatar_image.setPadding(0, 0, 0, 0)
        }
    }
    private val imageContainer: CircleImageView
        get() = avatar_image

    private var fullName: String = ""

    private var imageUrl: String = ""

    private var listener: (() -> Unit)? = null

    private var photoLoaded = false

    val currentImageUrl: String?
        get() {
            if (!imageUrl.startsWith("http")) {
                return imageUrl
            }
            val cashPath = "${context.cacheDir.absolutePath}/picasso-cache/"
            val files = File(cashPath).listFiles()
            files.filter { it.name.contains(".") }
                .filter { it.name.substring(it.name.lastIndexOf(".")) == ".0" }
                .forEach { file ->
                    val reader = BufferedReader(FileReader(file))
                    if (reader.readLine() == imageUrl) {
                        val oldFile = File("$cashPath${file.name.replace(".0", ".1")}")
                        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(
                            Date()
                        )
                        val dirPath = File(context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES), PHOTO_DIR_NAME)
                        val newFile = File("${dirPath.path}${File.separator}IMG_$timeStamp.$TYPE_FILE")
                        oldFile.copyTo(newFile, true)
                        return newFile.absolutePath
                    }
                }

            return null
        }

    fun photoLoaded(): Boolean {
        return photoLoaded
    }

    // if you want to use it, set before setup
    fun withListener(newListener: (() -> Unit)) = apply {
        listener = newListener
    }

    fun setup(name: String, surname: String, imageUrl: String? = null, localStorage: Boolean = false) = apply {
        if (name.isNotEmpty() && surname.isNotEmpty()) {
            fullName = "${surname[0]}${name[0]}".toUpperCase()
            setText()
            photoLoaded = false
        }
        if (!(imageUrl.isNullOrBlank() || imageUrl.isEmpty())) {
            this.imageUrl = imageUrl
            setImage(imageUrl, localStorage)
        }
    }

    fun invalidateByPath(path: String) = apply {
        Picasso.with(context).invalidate(File(path))
        Picasso.with(context).invalidate(path)
    }

    private fun setText() {
        avatar_text.visibility = View.VISIBLE
        avatar_text.text = fullName
        setBackGround()
    }

    private fun setImage(imageUrl: String, localStorage: Boolean) {
        if (localStorage) {
            Picasso.with(context)
                .load(File(imageUrl))
                .noPlaceholder()
                .fit()
                .centerCrop()
                .into(imageContainer)
            avatar_text.visibility = View.GONE
            photoLoaded = true
            setListener()
        } else {
            Picasso.with(context)
                .load(imageUrl)
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(imageContainer, object : Callback {
                    override fun onSuccess() {
                        avatar_text.visibility = View.GONE
                        photoLoaded = true
                        setListener()
                    }

                    override fun onError() {
                        setText()
                        photoLoaded = false
                        setListener()
                    }
                })
        }
    }

    private fun setListener(){
        listener?.let { imageContainer.setOnClickListener { if (isEnabled) it() } }
    }

    private fun setBackGround() {
        imageContainer.setImageDrawable(resources.getDrawable(R.drawable.avatar_background))
    }

    private fun initView(context: Context) {
        View.inflate(context, R.layout.avatar_view, this)
    }
}