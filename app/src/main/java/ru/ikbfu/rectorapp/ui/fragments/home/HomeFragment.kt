package ru.ikbfu.rectorapp.ui.fragments.home

import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.*
import kotlinx.android.synthetic.main.fragment_home.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.extensions.rxOnClickListener
import ru.ikbfu.rectorapp.model.data.server.model.SelectionCommitteeElement
import ru.ikbfu.rectorapp.model.data.server.model.ElementType
import ru.ikbfu.rectorapp.presenter.home.HomePresenter
import ru.ikbfu.rectorapp.presenter.view.HomeView
import ru.ikbfu.rectorapp.toothpick.DI
import ru.ikbfu.rectorapp.ui.adapters.TreeAdapter
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFragment
import toothpick.Toothpick

class HomeFragment : MvpBaseFragment(), HomeView, SwipeRefreshLayout.OnRefreshListener {

    override val layout: Int
        get() = R.layout.fragment_home
    private var seekbarProgress: Int? =null
    private var seekbarMax: Int? = null
    companion object {
        fun newInstance() = HomeFragment()
    }

    @InjectPresenter
    lateinit var presenter: HomePresenter

    @ProvidePresenter
    fun providePresenter(): HomePresenter = scope
        .getInstance(HomePresenter::class.java)


    private lateinit var treeAdapter: TreeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.checkCollapsing()
        presenter.getAllPriemApplications()
        initRecycler()
        presenter.getMainData()
        setClickListeners()

    }

    override fun setupAllAplicationsCount(value: Int){
        seekbarProgress = value
        setupProgress()
    }

    override fun initTree(data: List<SelectionCommitteeElement>) {

        seekBar.setOnTouchListener { v, event -> true }
        tree_recycler.adapter = treeAdapter
        val statsSum = data.first { x -> x.elementType == ElementType.PARENT_DATA }
        seekbarMax = statsSum.value
        setupProgress()
        setTreeData(data)
    }

    private fun setupProgress() {
        if(seekbarProgress!=null && seekbarMax!=null) {
            seekBar.progress = seekbarProgress!!
            seekBar.max = seekbarMax!!
            seekBar_tv.text =
                resources.getString(
                    R.string.stats_default,
                    seekbarProgress.toString(),
                    seekbarMax.toString()
                )
        }
    }

    private fun initRecycler() {
        treeAdapter = TreeAdapter(
            data = mutableListOf(),
            onItemSelected = { presenter.collapseChildsOf(it) },
            onItemChecked = {})
    }

    override fun setTreeData(data: List<SelectionCommitteeElement>) {
        treeAdapter.replaceData(data)
    }

    override fun userProfileCollapsed(value: Boolean) {
        if (value) {
            val view = home_root_container as ViewGroup
            TransitionManager.beginDelayedTransition(view, AutoTransition())
            user_info_container.visibility = View.GONE
            user_photo.visibility = View.GONE
            user_less_info_container.visibility = View.VISIBLE
        }
        else{
            val view = home_root_container as ViewGroup
            TransitionManager.beginDelayedTransition(view, AutoTransition())
            user_less_info_container.visibility = View.GONE
            user_info_container.visibility = View.VISIBLE
            user_photo.visibility = View.VISIBLE
        }
    }

    private fun setClickListeners() {
        expandLess.rxOnClickListener {
            presenter.collapseProfile(true)
        }.bind()

        expandMore.rxOnClickListener {
            presenter.collapseProfile(false)
        }.bind()

        exitBtn.rxOnClickListener { presenter.logout() }.bind()
        exit_less_Btn.rxOnClickListener { presenter.logout() }.bind()

        swipe_btn.setOnRefreshListener (this)
    }

    override fun onRefresh() {
        seekBar.progress = 0
        seekBar.max =0
        seekbarMax=null
        seekbarProgress=null
        presenter.refreshData()
    }

    override fun showRefreshProgress(isVisible: Boolean){
        swipe_btn.isRefreshing = isVisible
    }

    override fun setAvatar(name: String, surname: String, url: String?) {
        user_photo.setup(name, surname, url)
        user_less_photo.setup(name, surname, url)
        user_photo.enableBorder(false)
    }

    override fun setUserName(name: String, surname: String) {
        user_name_tv.text = resources.getString(R.string.nameSurname, name, surname)
        user_less_name_tv.text = resources.getString(R.string.nameSurname, name, surname)
    }

    override fun setUserDivision(division: String) {
        division_tv.text = resources.getString(R.string.division, division)
    }

    override fun setUserActivity(activity: String) {
        activity_tv.text = resources.getString(R.string.activity, activity)
    }

    override fun showDownLoadProgress(isVisible: Boolean) {
        showProgressDialog(isVisible)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(DI.APP_SCOPE))
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        if (presenter.authorized)
            presenter.getUserInfo()
    }
}