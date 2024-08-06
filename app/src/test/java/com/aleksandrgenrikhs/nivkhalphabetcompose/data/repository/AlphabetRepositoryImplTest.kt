package com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository

import android.content.Context
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.FirstTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.RevisionFirstMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.RevisionSecondMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.SecondTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.WordMapper
import kotlinx.serialization.json.Json
import org.mockito.Mockito.mock

class AlphabetRepositoryImplTest {
    private val context: Context = mock()
    private val wordMapper: WordMapper = mock()
    private val firstTaskMapper: FirstTaskMapper = mock()
    private val secondTaskMapper: SecondTaskMapper = mock()
    private val revisionFirstMapper: RevisionFirstMapper = mock()
    private val revisionSecondMapper: RevisionSecondMapper = mock()
    private val json: Json = mock()

    private val repository = AlphabetRepositoryImpl(
        context = context,
        wordMapper = wordMapper,
        firstTaskMapper = firstTaskMapper,
        secondTaskMapper = secondTaskMapper,
        revisionFirstMapper = revisionFirstMapper,
        revisionSecondMapper = revisionSecondMapper,
        json = json
    )
}