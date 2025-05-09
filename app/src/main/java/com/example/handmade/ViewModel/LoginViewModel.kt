import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.handmade.Model.LoginRequest
import com.example.handmade.Model.LoginResponse
import com.example.handmade.Rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResponse?>()
    val loginResult: LiveData<LoginResponse?> get() = _loginResult

    fun login(email: String, password: String) {
        val request = LoginRequest(email, password)
        RetrofitClient.api.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _loginResult.value = if (response.isSuccessful) response.body() else null
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.value = null
            }
        })
    }
}
