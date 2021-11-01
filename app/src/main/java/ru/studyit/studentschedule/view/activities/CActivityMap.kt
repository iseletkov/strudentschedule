package ru.studyit.studentschedule.view.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import ru.studyit.studentschedule.R
import ru.studyit.studentschedule.databinding.ActivityMapBinding

class CActivityMap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(58.008849, 56.218269)


        mMap.addMarker(MarkerOptions()
            .position(sydney)
            .title("Центр города Перми")
            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_baseline_favorite_border_24))
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        mMap.addPolygon(
            PolygonOptions()
                .add(LatLng(58.006700, 56.212937))
                .add(LatLng(58.007916, 56.220007))
                .add(LatLng(58.005506, 56.221573))
                .add(LatLng(58.004307, 56.214471))
                .fillColor(Color.GREEN)
        )
        mMap.clear()
    }
}