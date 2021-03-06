package dev.jonaz.vacegaming.musicbot.reaction.playlist

import dev.jonaz.vacegaming.musicbot.reaction.Reaction
import dev.jonaz.vacegaming.musicbot.reaction.ReactionHandler
import dev.jonaz.vacegaming.musicbot.reaction.ReactionMessageCase
import dev.jonaz.vacegaming.musicbot.service.PlaylistService
import dev.jonaz.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.entities.Member

@Reaction(2, "U+274c", ReactionMessageCase.PLAYLIST)
class PlaylistCancelReaction : ReactionHandler {
    private val playlistService by genericInject<PlaylistService>()

    override fun execute(member: Member) {
        playlistService.questionAnswers.offer(Pair(false, member))
    }
}
