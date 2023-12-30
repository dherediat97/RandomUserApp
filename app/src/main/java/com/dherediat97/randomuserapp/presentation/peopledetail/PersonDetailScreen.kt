package com.dherediat97.randomuserapp.presentation.peopledetail


import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Female
import androidx.compose.material.icons.rounded.Male
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dherediat97.randomuserapp.R
import com.dherediat97.randomuserapp.domain.model.Person
import com.dherediat97.randomuserapp.presentation.map.MapComposable
import com.dherediat97.randomuserapp.presentation.peoplelist.PersonListViewModel
import com.dherediat97.randomuserapp.ui.theme.Black
import com.dherediat97.randomuserapp.ui.theme.White
import com.dherediat97.randomuserapp.util.formatDate
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker


@Composable
fun PersonDetailScreen(
    viewModel: PersonListViewModel,
    backToList: () -> Unit,
    personEmail: String
) {
    viewModel.savedStateHandle["personEmail"] = personEmail
//    val person = Person(
//        name = Name("Doña", "Laura", last = "Navarro Martínez"),
//        cell = "1234545",
//        email = "lauranavarro@gmail.com",
//        gender = "male",
//        login = Login(UUID.randomUUID().toString(), "dheredia"),
//        nat = "es",
//        phone = "+34 665587115",
//        location = Location(
//            street = Street(1, "Calle Sin Nombre"),
//            city = "Córdoba",
//            coordinates = Coordinates("37.893605897259505", "-4.753414838528029"),
//            country = "España",
//            state = "Andalucia",
//            postCode = "14014",
//            timezone = Timezone("+1", "Europe/Madrid")
//        ),
//        registered = Registered("2018-05-11T05:51:59.390Z", age = 16),
//        picture = Picture(
//            "https://randomuser.me/api/portraits/women/60.jpg",
//            "https://randomuser.me/api/portraits/women/60.jpg",
//            "https://randomuser.me/api/portraits/women/60.jpg"
//        )
//    )

    val person by remember { mutableStateOf(viewModel.getUser()) }
    person?.let { person ->
        AppBar(person = person, backToList)
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
                    .padding(end = 16.dp)
                    .size(28.dp)
            )
        },
        value = name,
        singleLine = false,
        enabled = false,
        textStyle = TextStyle(color = Black),
        onValueChange = { },
        modifier = Modifier
            .padding(top = 16.dp, bottom = 8.dp)
            .height(55.dp)
            .drawBehind {
                val y = size.height
                drawLine(
                    LightGray, Offset(150f, y), Offset(size.width + 120, y), 1f
                )
            },
        label = {
            Text(
                text = label,
                color = Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 36.dp, end = 0.dp, top = 0.dp, bottom = 8.dp)
            )
        },
    )
}

@Composable
fun ContainerPersonDetails(person: Person) {
    val resources = LocalContext.current.resources
    val name by remember { mutableStateOf("${person.name.first} ${person.name.last}") }
    val gender by remember { mutableStateOf(if (person.gender == "male") "Hombre" else "Mujer") }
    val dateRegistered by remember { mutableStateOf(formatDate(person.registered.date)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp, 16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
        ) {
            CustomTextField("Nombre y apellidos", name, Icons.Rounded.AccountCircle)
            CustomTextField("Email", person.email, Icons.Rounded.Email)
            CustomTextField(
                "Género", gender, if (gender == "male") Icons.Rounded.Male else Icons.Rounded.Female
            )
            dateRegistered?.let { CustomTextField("Fecha registro", it, Icons.Rounded.DateRange) }
            CustomTextField("Teléfono", person.phone, Icons.Rounded.Phone)

            Text(
                text = "Dirección",
                modifier = Modifier.padding(start = 48.dp, top = 16.dp, bottom = 16.dp),
                fontSize = 14.sp,
                color = Gray
            )

            val coordinates = GeoPoint(
                person.location!!.coordinates.latitude.toDouble(),
                person.location.coordinates.longitude.toDouble()
            )
            Row {
                MapComposable(modifier = Modifier
                    .padding(start = 48.dp)
                    .width(300.dp)
                    .height(200.dp),
                    onLoad = { mapView ->
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
                        mapView.controller.zoomTo(16, 0)
                        mapView.controller.setCenter(coordinates)
                        runCatching {
                            val marker = Marker(mapView)
                            marker.position = coordinates
                            val d =
                                ResourcesCompat.getDrawable(resources, R.drawable.ic_marker, null)
                            val bitmap = d?.toBitmap()
                            val dr: Drawable = BitmapDrawable(
                                resources,
                                bitmap?.let {
                                    Bitmap.createScaledBitmap(
                                        it,
                                        (24.0f * resources.displayMetrics.density).toInt(),
                                        (24.0f * resources.displayMetrics.density).toInt(),
                                        true
                                    )
                                }
                            )
                            marker.icon = dr
                            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            mapView.overlays.add(marker)
                            mapView.invalidate()
                        }
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(person: Person, backToList: () -> Unit) {
    Scaffold(topBar = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(onClick = {
                    backToList()
                }) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        tint = White,
                        contentDescription = "icon back button",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(28.dp)
                    )
                    Text(
                        person.name.first,
                        color = White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 220.dp, start = 16.dp)
            ) {
                Image(
                    contentDescription = null,
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(person.picture.large)
                            .crossfade(true)
                            .build()
                    ),
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(200.dp))
                Icon(
                    Icons.Rounded.CameraAlt,
                    tint = Black,
                    contentDescription = "icon camera button",
                    modifier = Modifier
                        .padding(start = 0.dp, end = 16.dp, top = 62.dp)
                        .size(24.dp)
                )
                Icon(
                    Icons.Rounded.Edit,
                    tint = Black,
                    contentDescription = "icon edit button",
                    modifier = Modifier
                        .padding(start = 8.dp, end = 0.dp, top = 62.dp)
                        .size(24.dp)
                )

            }

        }
    }, content = { contentPadding ->
        Box(
            modifier = Modifier.padding(
                top = contentPadding.calculateTopPadding(),
                bottom = 16.dp
            )
        ) {
            ContainerPersonDetails(person = person)
        }
    })
}
