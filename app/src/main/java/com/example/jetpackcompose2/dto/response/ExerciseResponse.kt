package com.example.jetpackcompose2.dto.response

class ExerciseResponse(
    var name: String,
    var description: String,
    var videoUrl: String,     // Link đến video bài tập (trên cloud)
    var targetGoal: String    // Mục tiêu
)
