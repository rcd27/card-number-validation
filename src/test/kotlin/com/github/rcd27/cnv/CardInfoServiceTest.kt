package com.github.rcd27.cnv

import com.google.common.truth.Truth
import org.junit.Test

/**
 * In addition, connect to https://binlist.net API and put additional information about credit card
 * in response to user of your framework.
 * You are free to choose information about the card number, which you consider necessary to return to the user.
 */

class CardInfoServiceTest {

    @Test
    fun `Check connection for 200 response`() {
        val check200service = object : CardInfoService.Real() {
            override fun makeRequest(
                cardNumber: String,
                responseCodeConsumer: (Int) -> Unit,
                responseConsumer: (Response) -> Unit
            ) {
                super.makeRequest(
                    cardNumber,
                    { responseCode ->
                        Truth.assertThat(responseCode).isEqualTo(200)
                    },
                    {
                        // not handling response for this test case
                        println("Response body for check200service: ${it.responseBody}")
                    })
            }
        }
        check200service.getCardInfo(validCardNumber)
    }

    @Test
    fun `Check Card info handling`() {
        val realService = CardInfoService.Real()
        val cardInfo = realService.getCardInfo(validCardNumber)
        Truth.assertThat(cardInfo).isEqualTo(
            CardInfoService.CreditCard(
                "mastercard",
                "New World Immediate Debit"
            )
        )
    }
}