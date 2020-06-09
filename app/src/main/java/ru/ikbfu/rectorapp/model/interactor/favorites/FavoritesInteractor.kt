package ru.ikbfu.rectorapp.model.interactor.favorites

import ru.ikbfu.rectorapp.model.repository.favorites.FavoritesRepository
import javax.inject.Inject

class FavoritesInteractor @Inject constructor(private val repository: FavoritesRepository) {
    fun getFavorites() = repository.getTreeData()
        .andThen(repository.getStaffData())
        .andThen(repository.getGraduateData())
        .andThen(repository.getStudents())
        .andThen(repository.getFavorites())


    fun filterFavorites(text: String) =
        repository.filterFavorites(text)
}