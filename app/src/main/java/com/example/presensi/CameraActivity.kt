package com.example.presensi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class CameraActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    lateinit var  mScannerView : ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mScannerView = ZXingScannerView(this)
        setContentView(mScannerView)
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    override fun handleResult(result: Result?) {
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle("Berhasil")
        builder.setMessage("Anda berhasil presensi pada kelas:")
        builder.setMessage(result?.text)
        builder.setNeutralButton("Ok"){ _,_ ->
            finish()
            startActivity(Intent(this,MainActivity::class.java))
        }

        val alert :AlertDialog = builder.create()
        alert.show()
        mScannerView.resumeCameraPreview(this)
    }
}
