package ru.ikbfu.rectorapp.model.system

import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

class FlowRouter(private val appRouter: Router) : Router() {
    fun startFlow(screen:Screen){
        appRouter.navigateTo(screen)
    }

    fun newRootFlow(screen: Screen){
        appRouter.newRootScreen(screen)
    }

    fun finishFlow(){
        appRouter.exit()
    }
}