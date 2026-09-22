package com.example.androidextension

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidextension.databinding.ActivityMainBinding
import com.example.androidextension.utils.gone
import com.example.androidextension.utils.show
import com.example.androidextension.utils.toast
import com.example.androidextension.utils.toAcademicRanking
import com.example.androidextension.utils.trimmedText

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ban đầu ẩn phần kết quả
        binding.tvResult.gone()
        binding.tvRanking.gone()

        // Xử lý khi nhấn nút HIỂN THỊ
        binding.btnSubmit.setOnClickListener {

            // Lấy họ tên bằng Extension Function trimmedText()
            val name = binding.edtName.trimmedText()

            // Lấy GPA
            val gpaText = binding.edtGpa.trimmedText()

            // Kiểm tra họ tên
            if (name.isEmpty()) {
                binding.edtName.error = "Vui lòng nhập họ tên"
                binding.edtName.requestFocus()
                return@setOnClickListener
            }

            // Chuyển GPA sang Double
            val gpa = gpaText.toDoubleOrNull()

            // Kiểm tra GPA hợp lệ
            if (gpa == null) {
                binding.edtGpa.error = "Vui lòng nhập GPA"
                binding.edtGpa.requestFocus()
                return@setOnClickListener
            }

            // Kiểm tra GPA trong khoảng 0.0 -> 4.0
            if (gpa !in 0.0..4.0) {
                binding.edtGpa.error = "GPA phải từ 0.0 đến 4.0"
                binding.edtGpa.requestFocus()
                return@setOnClickListener
            }

            // Xóa thông báo lỗi
            binding.edtName.error = null
            binding.edtGpa.error = null

            // Hiển thị thông tin sinh viên
            binding.tvResult.text = """
                Họ tên: $name
                GPA: $gpa
            """.trimIndent()

            // Sử dụng Extension Function show()
            binding.tvResult.show()

            // Xếp loại học tập bằng Extension Function
            binding.tvRanking.text = gpa.toAcademicRanking()

            // Hiển thị kết quả xếp loại
            binding.tvRanking.show()

            // Thông báo bằng Context Extension Function toast()
            toast("Đã hiển thị thông tin sinh viên")
        }
    }
}