package com.ersuraj854.ganeshwebinfotech.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.ersuraj854.ganeshwebinfotech.R
import com.ersuraj854.ganeshwebinfotech.myapplication.Utlis
import com.ersuraj854.ganeshwebinfotech.myapplication.Utlis.Utils.checkPermissions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class FeedFragment : Fragment() {

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var currentlocationmarker: LatLng
    private lateinit var mapFragment: SupportMapFragment
private lateinit var  progress : ProgressBar

    private val callback = OnMapReadyCallback { googleMap ->

        googleMap.addMarker(
            MarkerOptions().position(currentlocationmarker)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentlocationmarker))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(8f))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feeds, container, false)
    }

    lateinit var currentlocation: TextView;
    var loc = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity().applicationContext)
        loc = Utlis.Utils.location(requireContext().applicationContext)
        checkpermission = checkPermissions(requireContext().applicationContext)
        currentlocation = view.findViewById(R.id.CurrentLocation)
        progress=view.findViewById(R.id.progressBar)

        if (loc) {


        }
        mapFragment = childFragmentManager.findFragmentById(R.id.mapid) as SupportMapFragment
        getLocation()


    }

    var checkpermission = false

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkpermission) {
            if (loc) {
                mFusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    val geocoder =
                        Geocoder(requireContext().applicationContext, Locale.getDefault())
                    val location: Location? = task.result
                    if (location != null) {
                        currentlocationmarker = LatLng(location.latitude, location.longitude)
                        Log.e("loca", location.latitude.toString())
                        mapFragment?.getMapAsync(callback)
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        this.apply {

                            currentlocation.setText(list[0].locality)

                        }
                    }
                }

            } else {

                Toast.makeText(
                    requireContext().applicationContext,
                    "Please turn on location",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            var requestpres = Utlis.Utils.requestPermissions(requireActivity());
        }


    }

}