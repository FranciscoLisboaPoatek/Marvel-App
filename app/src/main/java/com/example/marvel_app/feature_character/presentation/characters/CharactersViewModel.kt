package com.example.marvel_app.feature_character.presentation.characters

import androidx.lifecycle.MutableLiveData
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.presentation.MarvelTopAppBarViewModel

class CharactersViewModel : MarvelTopAppBarViewModel() {

    private val _charactersList = MutableLiveData<List<Character>>()
    val charactersList: MutableLiveData<List<Character>> = _charactersList

    init {
        _charactersList.value =
            mutableListOf(
                Character("1", "", "Iron Man", "Iron Man is a superhero appearing in American comic books published by Marvel Comics. Co-created by writer and editor Stan Lee, developed by scripter Larry Lieber, and designed by artists Don Heck and Jack Kirby, the character first appeared in Tales of Suspense #39 in 1963, and received his own title with Iron Man #1 in 1968. Shortly after his creation, Iron Man was a founding member of a superhero team, the Avengers, with Thor, Ant-Man, Wasp and the Hulk. Iron Man stories, individually and with the Avengers, have been published consistently since the character's creation.\n" +
                        "\n" +
                        "Iron Man is the superhero persona of Anthony Edward \"Tony\" Stark, a businessman and engineer who runs the company Stark Industries. Beginning his career as a weapons manufacturer, he is captured in a war zone, and his heart is severely injured by shrapnel. To sustain his heart and escape his captors, he builds a technologically advanced armor. After escaping, he continues using the armor as a superhero, creating more advanced models that grant him superhuman strength, flight, energy projection, and other abilities. The character was used to explore political themes, and early Iron Man stories were set in the Cold War. Later stories explored other themes, such as civil unrest, technological advancement, corporate espionage, alcoholism, and governmental authority.\n" +
                        "\n" +
                        "Major Iron Man stories include Demon in a Bottle (1979), Armor Wars (1987–1988), Extremis (2005), and Iron Man 2020 (2020). He is also a leading character in the company-wide stories Civil War (2006–2007), Dark Reign (2008–2009), and Civil War II (2016). Iron Man's supporting cast has produced additional superhero characters, including James Rhodes as War Machine, Pepper Potts as Rescue, and Riri Williams as Ironheart as well as reformed villains Black Widow and Hawkeye. Iron Man's list of enemies includes his archenemy, the Mandarin, as well as many supervillains of communist origin and many that double as business rivals for Stark.\n" +
                        "\n" +
                        "Robert Downey Jr. portrayed Tony Stark in Iron Man (2008), the first film of the Marvel Cinematic Universe, and continued to portray the character until his final appearance in Avengers: Endgame (2019). Downey's portrayal popularized the character, elevating Iron Man as one of Marvel's most recognizable superheroes."),
                Character("2", "", "Hulk", "The Hulk is a superhero appearing in American comic books published by Marvel Comics. Created by writer Stan Lee and artist Jack Kirby, the character first appeared in the debut issue of The Incredible Hulk."),
                Character("3", "", "Thor", "Thor Odinson is a character appearing in American comic books published by Marvel Comics. Created by artist Jack Kirby, writer Stan Lee, and scripter Larry Lieber, the character first appeared in Journey into Mystery #83 (August 1962), debuting in the Silver Age of Comic Books.[4] Thor is based on the Norse mythological god of the same name. He is the Asgardian god of thunder, whose enchanted hammer Mjolnir enables him to fly and manipulate weather, among his other superhuman attributes. A founding member of the superhero team the Avengers, Thor has a host of supporting characters and enemies.\n" +
                        "\n" +
                        "Thor has starred in several ongoing series and limited series, and appears in all volumes of the Avengers series. The character has been used in Marvel Comics merchandise, animated television series, films, video games, clothing and toys.[5][6]\n" +
                        "\n" +
                        "Chris Hemsworth portrays the character in several Marvel Cinematic Universe films: Thor (2011), The Avengers (2012), Thor: The Dark World (2013), Avengers: Age of Ultron (2015), Doctor Strange (2016, cameo), Thor: Ragnarok (2017), Avengers: Infinity War (2018), Avengers: Endgame (2019), and Thor: Love and Thunder (2022). Alternative versions of the character appear in the Disney+ series What If...? (2021)."),
                Character("1", "", "Iron Man", ""),
                Character("1", "", "Iron Man", ""),
                Character("1", "", "Iron Man", ""),
                Character("1", "", "Iron Man", ""),
            )
    }
}