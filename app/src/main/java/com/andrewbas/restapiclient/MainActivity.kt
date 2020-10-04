package com.andrewbas.restapiclient

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etClientId: EditText
    private lateinit var etClientSecret: EditText
    /*private lateinit var btnSearch: Button
    private lateinit var btnNext: Button
    private lateinit var btnPrev: Button*/

    private lateinit var videoNames: List<VideoItem>

    private lateinit var header: String

    private val query: String = "Learn+Flutter+%26+Dart+to+Build+iOS+%26+Android+Apps"

    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*etClientId = findViewById(R.id.etClientId)
        etClientSecret = findViewById(R.id.etClientSecret)

*/
        btnSearch.setOnClickListener(this)

        btnNext.setOnClickListener(this)

        btnPrev.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        header = getEncodedBase64(etClientId.toString(), etClientSecret.toString())
        if(v?.id == R.id.btnSearch){
            if (etClientSecret.text != null && etClientId.text != null){

                makeRequest(page)

            }else {
                showDialog(R.string.dialog_massage.toString())
            }
        }

        if(v?.id == R.id.btnNext){
            ++page
            makeRequest(page)
        }

        if(v?.id == R.id.btnPrev){
            --page
            makeRequest(page)
        }
    }

    fun getEncodedBase64(clientId: String, clientSecret: String): String{

        val byteArr = "$clientId:$clientSecret".toByteArray()

       return Base64.encodeToString(byteArr, Base64.DEFAULT)
    }

    private fun makeRequest(page: Int){
        val repository = SearchRepositoryProvider.provideSearchRepository()
        repository.searchVideos(header, query, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result -> if(result.total_count > 0){
                sortVideoNamesList(result.items)
            }else {
                showDialog(R.string.dialog_massage.toString())
            }

            }, { error ->
                error.printStackTrace()
            })

    }

    private fun sortVideoNamesList(items: List<VideoItem>){
        val sorted: List<VideoItem> = items.sortedBy {it.videoDuration}
        createRecyclerView(sorted)
    }
    private fun showDialog(message: String){

        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton(R.string.dialog_positive_btn
        ) { dialog, id ->
            if(id == Dialog.BUTTON_POSITIVE) {

                dialog.cancel()
            }
        }
         val dialog = builder.create()
        dialog.setMessage(message)
        dialog.show()

    }

    private fun createRecyclerView(videoNames: List<VideoItem>){
        val rv: RecyclerView = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)

        val rvAdapter = RvAdapter()

        rvAdapter.setItems(videoNames)
        rv.adapter = rvAdapter
    }

    object SearchRepositoryProvider {
        fun provideSearchRepository(): SearchRepository {
            return SearchRepository(apiService = UdemyAipService.create())
        }
    }

    class SearchRepository(val apiService: UdemyAipService) {
        fun searchVideos(header: String, query: String, page: Int): Observable<Result> {
            return apiService.search(header, query, page)
        }
    }


}
