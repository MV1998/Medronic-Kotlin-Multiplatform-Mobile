package com.mv1998.medronic.data.models

import com.mv1998.medronic.domain.models.Photo
import kotlinx.serialization.Serializable


@Serializable
data class PhotoDto(
    val albumId : Int,
    val id : Int,
    val title : String,
    val url : String,
    val thumbnailUrl : String
)

fun PhotoDto.toDomain() : Photo {
    return Photo(
        albumId = albumId,
        id = id,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
    )
}