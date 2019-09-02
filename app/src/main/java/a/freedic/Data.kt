package a.freedic

data class Translation(var displayText: String = "", var translation: String, var soundUrl: String)
data class Word(var hebrew: String = "", var transcription: String = "", var translation: String = "")
data class VerbConjugation(
        val singularM: Word, val singularF: Word,
        val pluralM: Word, val pluralF: Word,
        val past1Singular: Word, val past1Plural: Word,
        val past2SingularM: Word, val past2SingularF: Word,
        val past2PluralM: Word, val past2PluralF: Word,
        val past3SingularM: Word, val past3SingularF: Word,
        val past3Plural: Word,
        val future1Singular: Word, val future1Plural: Word,
        val future2SingularM: Word, val future2SingularF: Word,
        val future2PluralM: Word, val future2PluralF: Word,
        val future3SingularM: Word, val future3SingularF: Word,
        val future3PluralM: Word, val future3PluralF: Word,
        val imperativeSingularM: Word, val imperativeSingularF: Word,
        val imperativePluralM: Word, val imperativePluralF: Word,
        val infinitive: Word
)