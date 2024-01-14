package com.piotrbrus.donekotlin.navigation

import com.piotrbrus.donekotlin.util.Constans.WRITE_EDIT_SCREEN_ARGUMENT_KEY

sealed class Screen(val route: String) {
    object Authentication : Screen(route = "authentication_screen")
    object Home : Screen(route = "home_screen") //TODO: Future feature
    object TaskList : Screen(route = "task_list_screen")
    object NotesList : Screen(route = "notes_list_screen")
    object WriteEdit :
        Screen(route = "write_screen?$WRITE_EDIT_SCREEN_ARGUMENT_KEY={$WRITE_EDIT_SCREEN_ARGUMENT_KEY}") {
        fun passDiaryId(diaryId: String) = "write_screen?$WRITE_EDIT_SCREEN_ARGUMENT_KEY=$diaryId"
    }
}