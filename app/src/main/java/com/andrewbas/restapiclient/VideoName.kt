package com.andrewbas.restapiclient

data class VideoItem(val videoName: String, val videoDuration: String)

data class Result (val total_count: Int, val incomplete_results: Boolean, val items: List<VideoItem>)