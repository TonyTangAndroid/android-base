package com.jordifierro.androidbase.domain.executor;

interface ErrorMapper {

    Exception map(Throwable throwable);

}
