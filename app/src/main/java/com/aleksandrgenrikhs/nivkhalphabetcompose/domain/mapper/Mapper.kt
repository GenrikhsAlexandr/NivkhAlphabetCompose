package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper

interface Mapper<I, O> {

    fun map(input: I): O
}