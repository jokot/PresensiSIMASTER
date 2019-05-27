package com.example.presensi


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.presensi.Api.LogoutResponse
import com.example.presensi.Api.ProfileResponse
import kotlinx.android.synthetic.main.fragment_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserFragment : Fragment() {

    private val app = MainApp()
    private val service = app.service

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getProfile()
        setupClick()
    }

    private fun setupClick(){
        btn_keluar.setOnClickListener {
            logoutWeb()
        }
    }

    private fun logoutDatabase(){
        activity?.logout()
        startActivity(Intent(context,LoginActivity::class.java))
        activity?.finishAffinity()
    }

    private fun logoutWeb(){
        progress_bar.visibility = View.VISIBLE
        val tokenLogin = "Bearer ${activity?.getToken()}"
        app.logoutWeb(tokenLogin,{
            progress_bar.visibility = View.GONE
            activity?.toast(it)
        },{
            progress_bar.visibility = View.GONE
            logoutDatabase()
        },{
            if (it == "Unauthenticated."){
                logoutDatabase()
            }
            activity?.toast(it)
            progress_bar.visibility = View.GONE
        })
    }

    private fun getProfile(){
        progress_bar.visibility = View.VISIBLE
        val tokenLogin = "Bearer ${activity?.getToken()}"
        val call = service.getProfile(tokenLogin)
        call.enqueue(object :Callback<ProfileResponse>{
            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                activity?.progress_bar?.visibility = View.GONE
                activity?.toast(t.message.toString())
            }

            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                response.let { responseProfile->
                    if (responseProfile.isSuccessful) responseProfile.body().let {
                        activity?.progress_bar?.visibility = View.GONE
                        if(activity?.progress_bar?.visibility == View.GONE){
                            tv_nama.text = it?.name
                            tv_nim.text = it?.ni
                            tv_email.text = it?.email
                            if(it?.name == "Dandi Wiratsangka S"){
                                iv_user.setImageResource(R.drawable.foto_dandi)
                            }
                        }
                    }else responseProfile.errorBody()!!.string().let{

                        if(it == "{\"message\":\"Unauthenticated.\"}"){
                            activity?.progress_bar?.visibility = View.GONE
                            activity?.toast("Sesion Anda sudah habis, silakan login")
                            logoutDatabase()
                        }else{
                            activity?.progress_bar?.visibility = View.GONE
                            activity?.toast("Gagal menampilkan profile")
                            activity?.logD(it)
                        }

                    }

                }
            }

        })
    }
}
