package com.example.syftreposearchapp.data.model

import com.google.gson.annotations.SerializedName

data class GitRepoModel(

	@SerializedName("total_count") val total_count : Int,
	@SerializedName("items") val items : List<Items>
)