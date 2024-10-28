package com.example.lista1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    // Lista obiektow klasu question (pytań, listy odpowiedzi i indeksu poprawnej)
    private val questions = listOf(
        Question("Ile nóg ma kot?", listOf("Trzy", "Cztery", "Pięć", "Sześć"), 1),
        Question("Jakie jest typowe pożywienie dla kota domowego?", listOf("Liście", "Owady", "Mięso", "Mleko"), 2),
        Question("Jak nazywa się młody kot?", listOf("Szczenię", "Kociątko", "Juliusz Słowacki", "Jagnię"), 1),
        Question("Jaki dźwięk wydaje kot?", listOf("Miau", "Kwa", "Bee", "Chrum"), 0),
        Question("Co robi kot, gdy jest zadowolony?", listOf("Gryzie", "Macha ogonem", "Szczeka", "Mruczy"), 3),
        Question("Jakiego koloru są najczęściej oczy kota?", listOf("Czerwone", "Zielone", "Fioletowe", "Czarne"), 1),
        Question("Co koty lubią robić w wysokich miejscach?", listOf("Pić wode", "Gonić psy", "Siedzieć", "Tańczyć"), 2),
        Question("Jakiego rodzaju zmysł kot ma najbardziej rozwinięty?", listOf("Słuch", "Smak", "Wzrok", "Dotyk"), 0),
        Question("Ile palców mają koty w przednich łapkach?", listOf("Trzy", "Sześć", "Cztery", "Pięć"), 3),
        Question("Jaki zapach lubią koty?", listOf("Cytrusów", "Lawendy", "Kocimiętki", "Octu"), 2),

    )

    private var currentQuestionIndex = 0
    private var currentQuestionNumber = 1
    private var currentProgress = 0
    private var score = 0

    private lateinit var questionNumberText: TextView
    private lateinit var questionText: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton1: RadioButton
    private lateinit var radioButton2: RadioButton
    private lateinit var radioButton3: RadioButton
    private lateinit var radioButton4: RadioButton
    private lateinit var nextButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var summaryTextView: TextView
    private lateinit var summaryCardView: CardView
    private lateinit var CardView: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencje do widoków
        questionNumberText = findViewById(R.id.question_number_text)
        questionText = findViewById(R.id.question_text)
        radioGroup = findViewById(R.id.radioGroup)
        radioButton1 = findViewById(R.id.radioButton1)
        radioButton2 = findViewById(R.id.radioButton2)
        radioButton3 = findViewById(R.id.radioButton3)
        radioButton4 = findViewById(R.id.radioButton4)
        nextButton = findViewById(R.id.next_button)
        progressBar = findViewById(R.id.progress_Bar)
        summaryTextView = findViewById(R.id.summaryTextView)
        summaryCardView = findViewById(R.id.summaryCardView)
        CardView = findViewById(R.id.cardView)

        // Załadowanie pierwszego pytania
        loadQuestion()

        // Obsługa tego co ma sie wykonać po kliknieciu "Następne pytanie"
        nextButton.setOnClickListener {

            val selectedRadioButtonId = radioGroup.checkedRadioButtonId //pobieram id aktualnie kliknietego przycisku
            if (selectedRadioButtonId != -1) { //jesli nic nie bylo zaznaczone zwrqaca -1
                val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId) //referencja wybranego przycisku do nowej zmiennej
                val answerIndex = radioGroup.indexOfChild(selectedRadioButton) //pobieram indeks wybranego przycisku (bo id jest stringiem)

                // Sprawdzam czy odpowiedź jest poprawna
                if (answerIndex == questions[currentQuestionIndex].correctAnswerIndex) {
                    score++
                }

                // Przejdź do następnego pytania lub zakończ quiz
                currentQuestionIndex++
                currentQuestionNumber++
                currentProgress = currentQuestionIndex*10
                progressBar.setProgress(currentProgress)
                if (currentQuestionIndex < questions.size) {
                    loadQuestion()  // Załaduj kolejne pytanie
                } else {
                    // Wyświetl wynik
                    questionNumberText.text = "Gratulacje"
                    questionText.visibility = View.GONE
                    radioGroup.visibility = View.GONE
                    nextButton.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    CardView.visibility = View.GONE
                    summaryTextView.text = "Zdobyłeś $score pkt"
                    summaryCardView.visibility = View.VISIBLE
                    summaryTextView.visibility = View.VISIBLE

                    }
            } else {
                // Jeśli nie wybrano odpowiedzi wyswietla mały komunikat na ekranie
                Toast.makeText(this, "Wybierz odpowiedź!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Funkcja do załadowania pytania i odpowiedzi
    private fun loadQuestion() {
        val currentQuestion = questions[currentQuestionIndex]
        questionNumberText.text = "Pytanie $currentQuestionNumber/10"
        questionText.text = currentQuestion.questionText
        radioButton1.text = currentQuestion.answers[0]
        radioButton2.text = currentQuestion.answers[1]
        radioButton3.text = currentQuestion.answers[2]
        radioButton4.text = currentQuestion.answers[3]
        radioGroup.clearCheck()  // Odznacz poprzednią odpowiedź z przyciskow radio
    }
}
// klasa pytania
data class Question(
    val questionText: String,    // treść pytania
    val answers: List<String>,   // lista odpowiedzi   //answers[i]
    val correctAnswerIndex: Int  // indeks poprawnej odpowiedzi
)