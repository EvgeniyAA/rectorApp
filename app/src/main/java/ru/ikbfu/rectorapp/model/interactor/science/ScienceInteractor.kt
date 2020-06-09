package ru.ikbfu.rectorapp.model.interactor.science

import ru.ikbfu.rectorapp.model.repository.science.ScienceRepository
import javax.inject.Inject

class ScienceInteractor @Inject constructor(private val repository: ScienceRepository) {
    fun getScienceData() = repository.getScienceData()
    fun filterScienceData(text: String) = repository.filterScienceData(text)
}