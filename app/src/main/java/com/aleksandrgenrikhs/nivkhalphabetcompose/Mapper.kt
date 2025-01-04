package com.aleksandrgenrikhs.nivkhalphabetcompose

interface Mapper<I, O> {

    fun map(input: I): O
}
