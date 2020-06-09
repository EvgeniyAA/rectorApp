package ru.ikbfu.rectorapp.model.interactor.studentContingent

import io.reactivex.Completable
import io.reactivex.Single
import ru.ikbfu.rectorapp.model.data.server.model.Graduate
import ru.ikbfu.rectorapp.model.data.server.model.SelectionCommitteeElement
import ru.ikbfu.rectorapp.model.data.server.model.Student
import ru.ikbfu.rectorapp.model.repository.studentContingent.StudentContingentRepository
import javax.inject.Inject

class StudentContingentInteractor @Inject constructor(private val repository: StudentContingentRepository) {
    fun getAll(): Completable = repository.getGraduatesData().andThen(repository.getCurrentStudentsData()).andThen(repository.getGraduateYearsData())
    fun checked(item: Student): Single<List<Student>> = repository.checked(item)
    fun checkedGraduate(item: Graduate) = repository.checkedGraduate(item)
    fun getCurrentStudents() = repository.getCurrentStudents()
    fun getGraduates() = repository.getGraduates()
    fun getGraduateYears() = repository.getGraduatesYears()
    fun collapseChildsOf(item: Student) = repository.collapseChildsOf(item)

}