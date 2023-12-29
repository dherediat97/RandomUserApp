package com.dherediat97.randomuserapp.presentation.peopledetail

import android.view.MotionEvent
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dherediat97.randomuserapp.R
import com.dherediat97.randomuserapp.domain.model.Coordinates
import com.dherediat97.randomuserapp.domain.model.Location
import com.dherediat97.randomuserapp.domain.model.Login
import com.dherediat97.randomuserapp.domain.model.Name
import com.dherediat97.randomuserapp.domain.model.Person
import com.dherediat97.randomuserapp.domain.model.Picture
import com.dherediat97.randomuserapp.domain.model.Registered
import com.dherediat97.randomuserapp.domain.model.Street
import com.dherediat97.randomuserapp.domain.model.Timezone
import com.dherediat97.randomuserapp.ui.theme.Black
import org.koin.androidx.compose.koinViewModel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.util.UUID

@Composable
fun PersonDetailScreen(
    viewModel: PersonDetailViewModel = koinViewModel(),
) {
    val data by viewModel.uiState.collectAsState()

    val person = Person(
        name = Name("Doña", "Laura", last = "Navarro Martínez"),
        cell = "1234545",
        email = "lauranavarro@gmail.com",
        gender = "male",
        login = Login(UUID.randomUUID().toString(), "dheredia"),
        nat = "es",
        phone = "+34 665587115",
        location = Location(
            street = Street(1, "Calle Sin Nombre"),
            city = "Córdoba",
            coordinates = Coordinates("37.8931008", "-4.7588143"),
            country = "España",
            state = "Andalucia",
            postCode = "14014",
            timezone = Timezone("+1", "Europe/Madrid")
        ),
        registered = Registered("2018-05-11T05:51:59.390Z", age = 16),
        picture = Picture(
            "https://randomuser.me/api/portraits/men/60.jpg",
            "https://randomuser.me/api/portraits/men/60.jpg",
            "https://randomuser.me/api/portraits/men/60.jpg"
        )
    )
//    val person = data.person
    if (person != null) {
        AppBar(person = person)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(label: String, name: String, icon: ImageVector) {
    OutlinedTextField(
        leadingIcon = {
            Icon(
                icon,
                tint = Black,
                contentDescription = "icon person details",
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )
        },
        value = name,
        enabled = false,
        textStyle = TextStyle(color = Black),
        onValueChange = {},
        modifier = Modifier
            .padding(start = 24.dp, end = 4.dp, top = 0.dp, bottom = 0.dp)
            .drawBehind {
                val strokeWidth = density
                val y = size.height - strokeWidth / 2
                drawLine(
                    Gray,
                    Offset(150f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            },
        label = { Text(text = label) },
    )
}

@Composable
fun ContainerPersonDetails(person: Person) {
    val name by remember { mutableStateOf("${person.name.first} ${person.name.last}") }
    val gender by remember { mutableStateOf(if (person.gender == "male") "Hombre" else "Mujer") }

    Box(
        modifier = Modifier
            .padding(16.dp, 0.dp, 16.dp, 4.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(person.picture.large)
                    .crossfade(true)
                    .build(),
                contentDescription = "person thumbnail",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(96.dp)
                    .width(96.dp)
                    .clip(CircleShape)
            )
            CustomTextField("Nombre y apellidos", name, Icons.Rounded.AccountCircle)
            CustomTextField("Email", person.email, Icons.Rounded.Email)
            CustomTextField(
                "Género", gender, Icons.Rounded.AccountCircle
            )
            CustomTextField("Fecha registro", person.registered.date, Icons.Rounded.DateRange)
            CustomTextField("Teléfono", person.phone, Icons.Rounded.Phone)

            Text(text = "Direccion")

            val coordinates = GeoPoint(
                person.location!!.coordinates.latitude.toDouble(),
                person.location.coordinates.longitude.toDouble()
            )
            Row {
                val context = LocalContext.current
                AndroidView(
                    modifier = Modifier
                        .padding(start = 48.dp)
                        .width(250.dp)
                        .height(100.dp),
                    factory = {
                        val mapView = MapView(context)
                        mapView.isVerticalMapRepetitionEnabled = false
                        mapView.isHorizontalMapRepetitionEnabled = false
                        mapView.setExpectedCenter(coordinates)
                        mapView.setOnTouchListener(object : View.OnTouchListener {
                            override fun onTouch(p0: View?, event: MotionEvent?): Boolean {
                                return true
                            }
                        })
                        mapView.setMultiTouchControls(false)
                        mapView.setBuiltInZoomControls(false)
                        mapView.controller.zoomTo(17, 0)
                        val marker = Marker(mapView)
                        marker.position = coordinates
                        mapView
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(person: Person) {
    val name by remember { mutableStateOf("${person.name.first} ${person.name.last}") }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.background),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(person.picture.large)
                        .crossfade(true)
                        .build(),
                    contentDescription = "person thumbnail",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(83.dp)
                        .width(83.dp)
                        .clip(CircleShape)
                )
            }
        },
        content = { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                ContainerPersonDetails(person = person)
            }
        })
}