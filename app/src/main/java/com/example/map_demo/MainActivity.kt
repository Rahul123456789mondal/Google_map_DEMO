package com.example.map_demo

import android.Manifest
import android.Manifest.permission
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mLocationPermissionGranted: Boolean = false
    private val PERMISSION_REQUEST_CODE : Int = 9001
    private val TAG: String = "GoogleMaps"
    private lateinit var mMap: GoogleMap

    private val MYFLAT_LAT = 22.631413
    private val MYFLAT_LNG = 88.393155

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val supportMapFragment = SupportMapFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.map_fragment, supportMapFragment)
            .commit()
        supportMapFragment.getMapAsync(this)

        isServicesOk()
    }

    private fun isServicesOk() {
        if (mLocationPermissionGranted) {
            Toast.makeText(this, "Ready to Map!", Toast.LENGTH_SHORT).show()
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermission(permission.ACCESS_FINE_LOCATION)
                }
            }
        }
    }

    private fun requestPermission(permission: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(permission), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true
            Toast.makeText(this, "Permission granted...", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Toast.makeText(this, "Map is showing", Toast.LENGTH_SHORT).show()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        googleMap.isMyLocationEnabled = true // take my current location

        mMap = googleMap
        gotoLocation(MYFLAT_LAT, MYFLAT_LNG)

        mMap.uiSettings.isTiltGesturesEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(22.631413, 88.393155)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in ARKA_FlAT "))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun gotoLocation(lat : Double, lng : Double){
        val latLng : LatLng = LatLng(lat, lng)
        // Set the cameraview
            //val cameraUpdate : CameraUpdate = CameraUpdateFactory.newLatLng(latLng)
        // Set the camera using zoom view
             val cameraUpdate : CameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15F)
        mMap.moveCamera(cameraUpdate)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.maptype_none -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NONE
                true
            }
            R.id.maptype_normal -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.maptype_satellite -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.maptype_terrain -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            R.id.maptype_hybrid -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }

        }
    }
}


























































