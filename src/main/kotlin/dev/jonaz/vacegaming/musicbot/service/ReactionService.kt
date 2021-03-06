package dev.jonaz.vacegaming.musicbot.service

import dev.jonaz.vacegaming.musicbot.reaction.Reaction
import dev.jonaz.vacegaming.musicbot.reaction.ReactionHandler
import dev.jonaz.vacegaming.musicbot.reaction.ReactionMessageCase
import dev.jonaz.vacegaming.musicbot.reaction.ReactionModel
import net.dv8tion.jda.api.entities.Member
import org.atteo.classindex.ClassIndex

class ReactionService {
    private val reactions = mutableListOf<ReactionModel>()

    fun getReactions(messageCase: ReactionMessageCase): List<ReactionModel> {
        return reactions.filter { it.messageCase == messageCase }
    }

    fun getReactions(): MutableList<ReactionModel> {
        return reactions
    }

    fun initReactions() {
        val annotation = Reaction::class.java
        val classes = ClassIndex.getAnnotated(annotation)

        classes.forEach { clazz ->
            val annotationData = clazz.getAnnotation(annotation)

            ReactionModel(
                order = annotationData.order,
                emote = annotationData.emote,
                messageCase = annotationData.messageCase,
                clazz = clazz
            ).also(reactions::add)
        }
        reactions.sortBy { it.order }
    }

    fun execute(emote: String, member: Member) {
        reactions.findLast { it.emote == emote }?.run {
            val method = clazz.getMethod(ReactionHandler::execute.name, Member::class.java)
            val instance = clazz.getDeclaredConstructor().newInstance()

            method.invoke(instance, member)
        }
    }
}
