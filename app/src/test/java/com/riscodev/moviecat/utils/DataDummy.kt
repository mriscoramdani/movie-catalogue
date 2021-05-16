package com.riscodev.moviecat.utils

import com.google.gson.Gson
import com.riscodev.moviecat.data.source.local.entity.FavoriteEntity
import com.riscodev.moviecat.data.source.local.entity.MovieEntity
import com.riscodev.moviecat.data.source.local.entity.ShowEntity
import com.riscodev.moviecat.data.source.remote.response.MovieResponse
import com.riscodev.moviecat.data.source.remote.response.ShowResponse

object DataDummy {

    fun generateDummyMovies() : List<MovieEntity> {
        val movies = ArrayList<MovieEntity>()
        val movie = MovieEntity(
            "567189",
            "Mortal Kombat",
            "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
            "2021-04-07",
            7.7,
            "Released",
            "Action, Fantasy, Adventure",
            "/wChlWsVgwSd4ZWxTIm8PTEcaESz.png"
        )

        for (i in 0..10) {
            movies.add(movie)
        }
        return movies
    }

    fun generateDummyShows() : List<ShowEntity> {
        val shows = ArrayList<ShowEntity>()
        val show = ShowEntity(
            "95557",
            "Invincible",
            "Mark Grayson is a normal teenager except for the fact that his father is the most powerful superhero on the planet. Shortly after his seventeenth birthday, Mark begins to develop powers of his own and enters into his father’s tutelage.",
            "2021-03-26",
            8.9,
            "Released",
            "Animation, Fantasy, Adventure",
            1,
            "/wChlWsVgwSd4ZWxTIm8PTEcaESz.png"
        )

        for (i in 0..10) {
            shows.add(show)
        }
        return shows
    }

    fun generateFavorite() : FavoriteEntity = FavoriteEntity("1", MovieEntity.TYPE)

    fun generateRemoteDummyMovies() : List<MovieResponse> {
        val movies = ArrayList<MovieResponse>()
        val response = "{\"adult\":false,\"backdrop_path\":\"/6ELCZlTA5lGUops70hKdB83WJxH.jpg\",\"belongs_to_collection\":null,\"budget\":20000000,\"genres\":[{\"id\":28,\"name\":\"Action\"},{\"id\":14,\"name\":\"Fantasy\"},{\"id\":12,\"name\":\"Adventure\"}],\"homepage\":\"https://www.mortalkombatmovie.net\",\"id\":460465,\"imdb_id\":\"tt0293429\",\"original_language\":\"en\",\"original_title\":\"Mortal Kombat\",\"overview\":\"Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.\",\"popularity\":3345.294,\"poster_path\":\"/6Wdl9N6dL0Hi0T1qJLWSz6gMLbd.jpg\",\"production_companies\":[{\"id\":76907,\"logo_path\":\"/wChlWsVgwSd4ZWxTIm8PTEcaESz.png\",\"name\":\"Atomic Monster\",\"origin_country\":\"US\"},{\"id\":8000,\"logo_path\":\"/f8NwLg72BByt3eav7lX1lcJfe60.png\",\"name\":\"Broken Road Productions\",\"origin_country\":\"US\"},{\"id\":12,\"logo_path\":\"/iaYpEp3LQmb8AfAtmTvpqd4149c.png\",\"name\":\"New Line Cinema\",\"origin_country\":\"US\"},{\"id\":174,\"logo_path\":\"/IuAlhI9eVC9Z8UQWOIDdWRKSEJ.png\",\"name\":\"Warner Bros. Pictures\",\"origin_country\":\"US\"},{\"id\":2806,\"logo_path\":\"/vxOhCbpsRBh10m6LZ3HyImTYpPY.png\",\"name\":\"South Australian Film Corporation\",\"origin_country\":\"AU\"},{\"id\":13033,\"logo_path\":null,\"name\":\"NetherRealm Studios\",\"origin_country\":\"US\"}],\"production_countries\":[{\"iso_3166_1\":\"AU\",\"name\":\"Australia\"},{\"iso_3166_1\":\"US\",\"name\":\"United States of America\"}],\"release_date\":\"2021-04-07\",\"revenue\":50115000,\"runtime\":110,\"spoken_languages\":[{\"english_name\":\"Japanese\",\"iso_639_1\":\"ja\",\"name\":\"日本語\"},{\"english_name\":\"English\",\"iso_639_1\":\"en\",\"name\":\"English\"},{\"english_name\":\"Mandarin\",\"iso_639_1\":\"zh\",\"name\":\"普通话\"}],\"status\":\"Released\",\"tagline\":\"Get over here.\",\"title\":\"Mortal Kombat\",\"video\":false,\"vote_average\":7.7,\"vote_count\":2449}"
        val movie = Gson().fromJson(response, MovieResponse::class.java)

        for (i in 0..10) {
            movies.add(movie)
        }
        return movies
    }

    fun generateRemoteDummyShows() : List<ShowResponse> {
        val shows = ArrayList<ShowResponse>()
        val response = "{\"backdrop_path\":\"/6UH52Fmau8RPsMAbQbjwN3wJSCj.jpg\",\"created_by\":[],\"episode_run_time\":[45],\"first_air_date\":\"2021-03-26\",\"genres\":[{\"id\":16,\"name\":\"Animation\"},{\"id\":10759,\"name\":\"Action & Adventure\"},{\"id\":18,\"name\":\"Drama\"},{\"id\":10765,\"name\":\"Sci-Fi & Fantasy\"}],\"homepage\":\"https://www.amazon.com/dp/B08WJP55PR\",\"id\":95557,\"in_production\":true,\"languages\":[\"en\"],\"last_air_date\":\"2021-04-30\",\"last_episode_to_air\":{\"air_date\":\"2021-04-30\",\"episode_number\":8,\"id\":2832752,\"name\":\"WHERE I REALLY COME FROM\",\"overview\":\"Mark must prove he's become the hero he's always wanted to be by stopping an unstoppable force.\",\"production_code\":\"INVI 108\",\"season_number\":1,\"still_path\":\"/ijDV8Z23iR5B2ftx0WggiXbfqGi.jpg\",\"vote_average\":9.5,\"vote_count\":2},\"name\":\"Invincible\",\"next_episode_to_air\":null,\"networks\":[{\"name\":\"Amazon\",\"id\":1024,\"logo_path\":\"/ifhbNuuVnlwYy5oXA5VIb2YR8AZ.png\",\"origin_country\":\"\"}],\"number_of_episodes\":8,\"number_of_seasons\":1,\"origin_country\":[\"US\"],\"original_language\":\"en\",\"original_name\":\"Invincible\",\"overview\":\"Mark Grayson is a normal teenager except for the fact that his father is the most powerful superhero on the planet. Shortly after his seventeenth birthday, Mark begins to develop powers of his own and enters into his father’s tutelage.\",\"popularity\":1055.08,\"poster_path\":\"/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg\",\"production_companies\":[{\"id\":20580,\"logo_path\":\"/tkFE81jJIqiFYPP8Tho57MXRQEx.png\",\"name\":\"Amazon Studios\",\"origin_country\":\"US\"},{\"id\":151645,\"logo_path\":null,\"name\":\"Skybound North\",\"origin_country\":\"CA\"},{\"id\":50032,\"logo_path\":null,\"name\":\"Skybound Entertainment\",\"origin_country\":\"US\"}],\"production_countries\":[{\"iso_3166_1\":\"CA\",\"name\":\"Canada\"},{\"iso_3166_1\":\"US\",\"name\":\"United States of America\"}],\"seasons\":[{\"air_date\":\"2021-03-26\",\"episode_count\":8,\"id\":136020,\"name\":\"Season 1\",\"overview\":\"\",\"poster_path\":\"/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg\",\"season_number\":1}],\"spoken_languages\":[{\"english_name\":\"English\",\"iso_639_1\":\"en\",\"name\":\"English\"}],\"status\":\"Returning Series\",\"tagline\":\"\",\"type\":\"Scripted\",\"vote_average\":8.9,\"vote_count\":1628}"
        val show = Gson().fromJson(response, ShowResponse::class.java)

        for (i in 0..10) {
            shows.add(show)
        }
        return shows
    }
}