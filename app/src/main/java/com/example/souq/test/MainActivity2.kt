//package com.example.productsadder
//
//import android.net.Uri
//import android.os.Bundle
//import android.os.PersistableBundle
//import android.view.Menu
//import android.view.MenuItem
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.productsadder.databinding.ActivityMainBinding
//import com.skydoves.colorpickerview.ColorEnvelope
//import com.skydoves.colorpickerview.ColorPickerDialog
//import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
//
//class MainActivity2 : AppCompatActivity() {
//    lateinit var binding: ActivityMainBinding
//    private val selectedImages = mutableListOf<Uri>()
//    private val selectedColors = mutableListOf<Int>()
//
//    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
//        super.onCreate(savedInstanceState, persistentState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.buttonColorPicker.setOnClickListener {
//            ColorPickerDialog.Builder(this)
//                .setTitle("ProductColor")
//                .setPositiveButton("Select", object : ColorEnvelopeListener {
//                    override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {
//                        envelope?.let {
//                            selectedColors.add(it.color)
//                            updateColors()
//                        }
//                    }
//
//                }).show()
//        }
//
//    }
//
//    private fun updateColors() {
//        var colors=""
//        selectedColors.forEach {
//            colors="$colors${Integer.toHexString(it)}"
//        }
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.toolbar_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.saveProduct) {
//            val productValidation = validationInfo()
//            if (!productValidation) {
//                Toast.makeText(this, "Check your inputs", Toast.LENGTH_SHORT).show()
//
//                return false
//            }
//            saveProduct()
//
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun saveProduct() {
//        val name = binding.edName.text.toString().trim()
//        val category = binding.edCategory.text.toString().trim()
//        val price = binding.edPrice.text.toString().trim()
//        val offerPrecentage = binding.edOfferPercentage.text.toString().trim()
//        val sizes = getsizesList(binding.edSizes.text.toString().trim())
//    }
//
//    private fun getsizesList(sizes: String): List<String>? {
//        if (sizes.isEmpty())
//            return null
//        val sizesList = sizes.split(",")
//        return sizesList
//
//
//    }
//
//    private fun validationInfo(): Boolean {
//        if (binding.edPrice.text.toString().trim().isEmpty())
//            return false
//        if (binding.edName.text.toString().trim().isEmpty())
//            return false
//        if (binding.edCategory.text.toString().trim().isEmpty())
//            return false
//        if (selectedImages.isEmpty())
//            return false
//        return true
//
//    }
//}