package com.example.quizapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.network.CountryApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel(private val countryApiService: CountryApiService) : ViewModel() {

    private val _questions = MutableStateFlow<List<CountryQuestion>>(emptyList())
    val questions: StateFlow<List<CountryQuestion>> = _questions

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    private val _selectedOption = MutableStateFlow<Int?>(null)
    val selectedOption: StateFlow<Int?> = _selectedOption

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _isSubmitted = MutableStateFlow(false)
    val isSubmitted: StateFlow<Boolean> = _isSubmitted

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val countries = countryApiService.getAllCountries()
                println("hello $countries")
                val questions = generateFlagQuestions(countries)
                _questions.value = questions
            } catch (e: Exception) {
                _error.value = "Failed to load countries: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectOption(index: Int) {
        if (!_isSubmitted.value) _selectedOption.value = index
    }

    fun submitAnswer() {
        if (_selectedOption.value == null) return
        _isSubmitted.value = true
        if (isAnswerCorrect()) {
            _score.value++
        }
        // Removed automatic nextQuestion() call - user must click NEXT button
    }

    fun nextQuestion() {
        if (_currentQuestionIndex.value < _questions.value.size - 1) {
            _currentQuestionIndex.value++
            _selectedOption.value = null
            _isSubmitted.value = false
        } else {
            // Last question reached
            _isSubmitted.value = false
        }
    }

    fun previousQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value--
            _selectedOption.value = null
        }
    }

    fun isAnswerCorrect(): Boolean {
        val currentQuestion = _questions.value.getOrNull(_currentQuestionIndex.value)
        val selected = _selectedOption.value
        return currentQuestion != null && selected != null &&
                currentQuestion.options[selected] == currentQuestion.correctAnswer
    }

    fun getCurrentQuestion(): CountryQuestion? {
        return _questions.value.getOrNull(_currentQuestionIndex.value)
    }

    fun getProgress(): Pair<Int, Int> {
        val current = _currentQuestionIndex.value + 1
        val total = _questions.value.size
        return current to total
    }

    fun generateFlagQuestions(countries: List<Country>, count: Int = 10): List<CountryQuestion> {
        val questions = mutableListOf<CountryQuestion>()
        val shuffled = countries.shuffled().take(count)

        shuffled.forEach { country ->
            val options = (countries - country)
                .shuffled()
                .take(3)
                .map { it.name.common } + country.name.common

            questions.add(
                CountryQuestion(
                    question = "Which country does this flag belong to?",
                    flagUrl = country.flags.png,
                    correctAnswer = country.name.common,
                    options = options.shuffled()
                )
            )
        }

        return questions
    }

    fun updateName(name: String) {
        _name.value = name
    }

    fun reset() {
        _currentQuestionIndex.value = 0
        _selectedOption.value = null
        _score.value = 0
        _name.value = ""
        loadQuestions()
    }
}