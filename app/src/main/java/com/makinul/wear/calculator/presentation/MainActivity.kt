/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.makinul.wear.calculator.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.tooling.preview.devices.WearDevices
import com.makinul.wear.calculator.R
import com.makinul.wear.calculator.presentation.theme.WearCalculatorTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearCalculatorApp()
        }
    }
}

@Composable
fun WearApp() {
    WearCalculatorTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            TimeText()
            WearableCalculatorScreen()
        }
    }
}

/**
 * Main Composable for the Wearable Calculator UI.
 * This composable sets up the Scaffold and the CalculatorContent.
 */
@Composable
fun WearableCalculatorScreen() {
    // Scaffold provides a basic layout structure for Wear OS apps.
    // It includes a Vignette for visual depth on round screens.
    Scaffold(
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        timeText = { TimeText() } // Displays current time, common for Wear OS apps
    ) {
        // Content of the calculator UI
        CalculatorContent()
    }
}

/**
 * Composable that contains the core calculator UI and logic.
 * It manages the input expression and the calculated result.
 */
@Composable
fun CalculatorContent() {
    // State to hold the current input expression displayed on the calculator
    var expression by remember { mutableStateOf("") }
    // State to hold the calculated result
    var result by remember { mutableStateOf("") }

    // Column to arrange the display and buttons vertically
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Dark background for the calculator
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        // Calculator display area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Takes available vertical space
                .padding(bottom = 4.dp),
            horizontalAlignment = Alignment.End, // Align text to the right
            verticalArrangement = Arrangement.Center
        ) {
            // Display for the current expression
            Text(
                text = expression.ifEmpty { "0" }, // Show "0" if expression is empty
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.End,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            // Display for the result
            Text(
                text = result,
                color = Color.Gray,
                fontSize = 20.sp,
                textAlign = TextAlign.End,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Calculator buttons grid
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f), // Takes more vertical space for buttons
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Row 1: Clear, Division
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(text = "C", color = Color.Red) {
                    expression = "" // Clear the expression
                    result = "" // Clear the result
                }
                CalculatorButton(text = "÷") { expression += "÷" }
            }

            // Row 2: 7, 8, 9, Multiplication
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(text = "7") { expression += "7" }
                CalculatorButton(text = "8") { expression += "8" }
                CalculatorButton(text = "9") { expression += "9" }
                CalculatorButton(text = "×") { expression += "×" }
            }

            // Row 3: 4, 5, 6, Subtraction
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(text = "4") { expression += "4" }
                CalculatorButton(text = "5") { expression += "5" }
                CalculatorButton(text = "6") { expression += "6" }
                CalculatorButton(text = "-") { expression += "-" }
            }

            // Row 4: 1, 2, 3, Addition
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(text = "1") { expression += "1" }
                CalculatorButton(text = "2") { expression += "2" }
                CalculatorButton(text = "3") { expression += "3" }
                CalculatorButton(text = "+") { expression += "+" }
            }

            // Row 5: 0, Decimal, Equals
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(
                    text = "0",
                    modifier = Modifier.weight(2f)
                ) { expression += "0" } // Wider button for 0
                CalculatorButton(text = ".") {
                    // Prevent multiple decimals in a number
                    if (expression.isEmpty() || expression.last().isDigit()) {
                        expression += "."
                    }
                }
                CalculatorButton(text = "=", color = MaterialTheme.colors.primary) {
                    // Calculate the result when "=" is pressed
                    result = try {
                        evaluateExpression(expression).toString()
                    } catch (e: Exception) {
                        "Error" // Display "Error" for invalid expressions
                    }
                }
            }
        }
    }
}

/**
 * Reusable Composable for a calculator button.
 *
 * @param text The text to display on the button.
 * @param modifier Modifier for custom button styling.
 * @param color Background color of the button.
 * @param onClick Lambda to be invoked when the button is clicked.
 */
@Composable
fun RowScope.CalculatorButton(
    text: String,
    modifier: Modifier = Modifier.weight(1f), // Default weight for even distribution
    color: Color = Color.DarkGray, // Default button color
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1f) // Ensure buttons are square
            .padding(4.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        shape = MaterialTheme.shapes.small // Rounded corners for buttons
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

///**
// * Evaluates a mathematical expression string.
// * This is a simplified evaluator for basic arithmetic operations.
// * It handles addition, subtraction, multiplication, and division.
// *
// * @param expression The mathematical expression as a string (e.g., "10+5*2").
// * @return The result of the evaluation as a Double.
// * @throws IllegalArgumentException if the expression is invalid or contains division by zero.
// */
//fun evaluateExpression(expression: String): Double {
//    // Replace custom operators with standard ones for easier parsing
//    val formattedExpression = expression
//        .replace("÷", "/")
//        .replace("×", "*")
//
//    // Use a simple approach: split by operators and perform operations sequentially.
//    // This does NOT handle operator precedence (e.g., multiplication before addition)
//    // beyond the order of operations in the list. For a full-fledged calculator,
//    // a Shunting-yard algorithm or similar would be required.
//
//    val numbers = formattedExpression.split(Regex("[+\\-*/]")).map { it.toDouble() }
//    val operators = formattedExpression.filter { it == '+' || it == '-' || it == '*' || it == '/' }
//
//    if (numbers.isEmpty()) {
//        throw IllegalArgumentException("Invalid expression")
//    }
//
//    var currentResult = numbers[0]
//    for (i in 0 until operators.length) {
//        val operator = operators[i]
//        val nextNumber = numbers[i + 1]
//
//        when (operator) {
//            '+' -> currentResult += nextNumber
//            '-' -> currentResult -= nextNumber
//            '*' -> currentResult *= nextNumber
//            '/' -> {
//                if (nextNumber == 0.0) {
//                    throw IllegalArgumentException("Division by zero")
//                }
//                currentResult /= nextNumber
//            }
//        }
//    }
//
//    // Round to a reasonable number of decimal places to avoid floating point inaccuracies
//    return (currentResult * 1000000.0).roundToInt() / 1000000.0
//}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearableCalculatorScreen()
}

@Composable
fun WearCalculatorUI(
    modifier: Modifier = Modifier,
    displayText: String = "",
    onButtonClick: (String) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        timeText = { TimeText() }
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display area
            Text(
                text = displayText,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Calculator buttons (4x4 grid)
            val buttons = listOf(
                listOf("7", "8", "9", "/"),
                listOf("4", "5", "6", "×"),
                listOf("1", "2", "3", "-"),
                listOf("C", "0", "=", "+")
            )

            buttons.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { label ->
                        CalculatorButton(label = label) {
                            onButtonClick(label)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    label: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(35.dp),
        shape = CircleShape
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun WearCalculatorUIPreview() {
    WearCalculatorUI { }
}

fun evaluateExpression(expr: String): Double {
    val tokens = tokenize(expr)
    val postfix = infixToPostfix(tokens)
    return evaluatePostfix(postfix)
}

fun tokenize(expr: String): List<String> {
    val regex = Regex("""\d+(\.\d+)?|[+*/()-]""")
    return regex.findAll(expr).map { it.value }.toList()
}

fun infixToPostfix(tokens: List<String>): List<String> {
    val output = mutableListOf<String>()
    val stack = ArrayDeque<String>()
    val precedence = mapOf("+" to 1, "-" to 1, "*" to 2, "/" to 2)

    for (token in tokens) {
        when {
            token.toDoubleOrNull() != null -> output.add(token)
            token == "(" -> stack.addLast(token)
            token == ")" -> {
                while (stack.isNotEmpty() && stack.last() != "(") {
                    output.add(stack.removeLast())
                }
                stack.removeLast() // remove "("
            }
            else -> {
                while (stack.isNotEmpty() && precedence.getOrDefault(token, 0) <= precedence.getOrDefault(stack.last(), 0)) {
                    output.add(stack.removeLast())
                }
                stack.addLast(token)
            }
        }
    }

    while (stack.isNotEmpty()) {
        output.add(stack.removeLast())
    }

    return output
}

fun evaluatePostfix(tokens: List<String>): Double {
    val stack = ArrayDeque<Double>()

    for (token in tokens) {
        when {
            token.toDoubleOrNull() != null -> stack.addLast(token.toDouble())
            else -> {
                val b = stack.removeLast()
                val a = stack.removeLast()
                val result = when (token) {
                    "+" -> a + b
                    "-" -> a - b
                    "*" -> a * b
                    "/" -> a / b
                    else -> throw IllegalArgumentException("Invalid operator: $token")
                }
                stack.addLast(result)
            }
        }
    }

    return stack.last()
}

@Composable
fun WearCalculatorApp() {
    var display by remember { mutableStateOf("") }

    fun calculateResult(expression: String): String {
        return try {
            val cleanExpr = expression.replace("×", "*").replace("÷", "/")
            val result = evaluateExpression(cleanExpr)
            result.toString()
        } catch (e: Exception) {
            "Error"
        }
    }

    WearCalculatorUI(
        displayText = display,
        onButtonClick = { label ->
            when (label) {
                "=" -> {
                    display = calculateResult(display)
                }
                "C" -> {
                    display = ""
                }
                else -> {
                    if (display == "Error") display = ""
                    display += label
                }
            }
        }
    )
}