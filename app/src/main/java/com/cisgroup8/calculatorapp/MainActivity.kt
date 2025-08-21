package com.cisgroup8.calculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cisgroup8.calculatorapp.ui.theme.CalculatorAppTheme
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    val buttonRows = listOf(
        listOf("C", "7", "8", "9", "÷"),
        listOf("4", "5", "6", "×"),
        listOf("1", "2", "3", "-"),
        listOf("0", ".", "=", "+")
    )

    val functionButtons = setOf("C", "÷", "×", "+", "-", "=")

    val defaultOnClick: (String) -> Unit = { expression += it }

    val onClickMap = mapOf(
        "C" to { _: String ->
            expression = ""
            result = ""
        },
        "=" to { _: String -> result = solveExpression(expression) }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Display(expression, result)


        Spacer(modifier = Modifier.weight(1f))


        buttonRows.forEach { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                row.forEach { buttonText ->
                    CalculatorButton(
                        text = buttonText,
                        isFunction = buttonText in functionButtons,
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp),
                        onClick = onClickMap[buttonText] ?: defaultOnClick
                    )
                }
            }
        }
    }
}




@Composable
fun Display(expression: String, result: String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Text(
            expression,
            modifier = Modifier.fillMaxWidth().testTag("expression_input"),
            maxLines = 3,
            style = TextStyle(
                fontSize = 48.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            result,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 48.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End
            )
        )
    }
}

fun solveExpression(expression: String): String {
    if (expression.isEmpty()) return ""

    val modifiedExpression = expression
        .replace("×", "*")
        .replace("÷", "/")
        .replace("%", "/100")

    val result = Expression(modifiedExpression).calculate()

    return when {
        result.isNaN() -> "Error"
        result % 1.0 == 0.0 -> result.toInt().toString()
        else -> result.toString()
    }
}

fun delCharacter(expression: String) = expression.takeIf { it.isNotEmpty() }?.dropLast(1) ?: ""

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    text: String = "0",
    isFunction: Boolean = false,
    onClick: (String) -> Unit = {}
) {
    val tag = "button_$text"

    val containerColors = when {
        isFunction && (text == "C" || text == "=") -> MaterialTheme.colorScheme.primary
        isFunction -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.tertiary
    }

    Button(
        modifier = modifier
            .size(72.dp)
            .clip(CircleShape)
            .testTag(tag),
        onClick = { onClick(text) },
        colors = ButtonDefaults.buttonColors(containerColor = containerColors)
    ) {
        Text(text = text, style = TextStyle(fontSize = 24.sp))
    }
}

@Preview
@Composable
fun CalculatorButtonPreview() {
    CalculatorButton()
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen()
}
