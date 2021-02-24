package com.t.demoprojects

data class QuestionModel(var question:String, var option:List<OptionModel>,
                         var answer:String?="") {
}