package com.fuadhev.tradewave.ui.profile.payment.addCard


import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fuadhev.tradewave.common.base.BaseFragment
import com.fuadhev.tradewave.common.utils.Extensions.gone
import com.fuadhev.tradewave.common.utils.Extensions.showMessage
import com.fuadhev.tradewave.common.utils.Extensions.visible
import com.fuadhev.tradewave.databinding.FragmentAddCardBinding
import com.fuadhev.tradewave.domain.model.CardUiModel
import com.fuadhev.tradewave.ui.profile.payment.CardUiState
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCardFragment : BaseFragment<FragmentAddCardBinding>(FragmentAddCardBinding::inflate) {


    private val viewModel by viewModels<AddCardViewModel>()


    override fun observeEvents() {
        viewModel.cardState.observe(viewLifecycleOwner) {
            handleState(it)
        }

    }

    override fun onCreateFinish() {

    }

    override fun setupListeners() {

        setTextWatcher()

        with(binding) {
            btnAddCart.setOnClickListener {

                //bu yoxlanisi validationda yazacam
                if(etEnterCardNumber.text.toString().isNotEmpty()&&
                etEnterCardNumber.text.toString().isNotEmpty()&&
                etExpirationDate.text.toString().isNotEmpty()&&
                etSecurityCode.text.toString().isNotEmpty()&&
                etCardHolder.text.toString().isNotEmpty()){

                    val cardUiModel = CardUiModel(
                        etEnterCardNumber.text.toString(),
                        etEnterCardNumber.text.toString(),
                        etExpirationDate.text.toString(),
                        etSecurityCode.text.toString(),
                        etCardHolder.text.toString()
                    )
                    viewModel.addCard(cardUiModel)
                }

            }
            ibBack.setOnClickListener {
                findNavController().popBackStack()
            }

        }

    }

    private fun handleState(state: CardUiState) {
        with(binding) {
            when (state) {
                is CardUiState.Loading -> {
                    loading.visible()
                }
                is CardUiState.SuccessAddCard -> {
                    loading.gone()
                    if (state.success) {
                        requireActivity().showMessage("Card Added", FancyToast.SUCCESS)
                    } else {
                        requireActivity().showMessage("Card Not Added", FancyToast.SUCCESS)
                    }

                }

                is CardUiState.Error -> {
                    loading.gone()
                    requireActivity().showMessage(state.message, FancyToast.SUCCESS)
                }


                else -> {}
            }
        }

    }

    private fun setTextWatcher() {
        with(binding) {
            val cardNumberTextWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {

                    val inputText = s.toString().replace(" ", "")
                    val formattedText = StringBuilder()
                    for (i in inputText.indices) {
                        if (i > 0 && i % 4 == 0) {
                            formattedText.append(" ")
                        }
                        formattedText.append(inputText[i])
                    }

                    // Düzenlenmiş metni tekrar Edittext'e yerleştirin
                    etEnterCardNumber.removeTextChangedListener(this) 
                    etEnterCardNumber.setText(formattedText.toString())
                    etEnterCardNumber.setSelection(formattedText.length)
                    etEnterCardNumber.addTextChangedListener(this)
                }
            }

            val expirationTextWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Text değişmeden önceki durum
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Text değiştiğinde yapılacak işlemler
                }

                override fun afterTextChanged(s: Editable?) {
                    // Text değiştikten sonra, metni düzenleyin
                    val inputText =
                        s.toString().replace("/", "") // Eski "/" karakterlerini kaldırın
                    val formattedText = StringBuilder()

                    for (i in inputText.indices) {
                        formattedText.append(inputText[i])
                        if (i == 1) {
                            formattedText.append("/") // İkinci karakterden sonra "/" ekleyin
                        }
                    }

                    // Düzenlenmiş metni tekrar Edittext'e yerleştirin
                    etExpirationDate.removeTextChangedListener(this)
                    etExpirationDate.setText(formattedText.toString())
                    etExpirationDate.setSelection(formattedText.length)
                    etExpirationDate.addTextChangedListener(this)
                }
            }
            etExpirationDate.addTextChangedListener(expirationTextWatcher)

            etEnterCardNumber.addTextChangedListener(cardNumberTextWatcher)
        }
    }

}