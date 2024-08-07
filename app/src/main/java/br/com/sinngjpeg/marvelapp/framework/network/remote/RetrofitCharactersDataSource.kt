package br.com.sinngjpeg.marvelapp.framework.network.remote

import br.com.jpegsinng.core.data.repository.CharactersRemoteDataSource
import br.com.jpegsinng.core.domain.model.CharacterPaging
import br.com.jpegsinng.core.domain.model.Comic
import br.com.jpegsinng.core.domain.model.Event
import br.com.sinngjpeg.marvelapp.framework.network.MarvelApi
import br.com.sinngjpeg.marvelapp.framework.network.response.toCharacterModel
import br.com.sinngjpeg.marvelapp.framework.network.response.toComicModel
import br.com.sinngjpeg.marvelapp.framework.network.response.toEventModel
import javax.inject.Inject

class RetrofitCharactersDataSource @Inject constructor(
    private val marvelApi: MarvelApi
) : CharactersRemoteDataSource {

    override suspend fun fetchCharacters(queries: Map<String, String>): CharacterPaging {
        val data = marvelApi.getCharacters(queries).data
        val characters = data.results.map {
            it.toCharacterModel()
        }

        return CharacterPaging(
            data.offset,
            data.total,
            characters
        )
    }

    override suspend fun fetchComics(characterId: Int): List<Comic> {
        return marvelApi.getComics(characterId).data.results.map {
            it.toComicModel()
        }
    }

    override suspend fun fetchEvents(characterId: Int): List<Event> {
        return marvelApi.getEvents(characterId).data.results.map {
            it.toEventModel()
        }
    }
}