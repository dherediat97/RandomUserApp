package com.dherediat97.randomuserapp.presentation.peoplecard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.dherediat97.randomuserapp.domain.model.Person
import com.dherediat97.randomuserapp.ui.theme.Black
import com.dherediat97.randomuserapp.ui.theme.Grey

@Composable
fun PersonCardItem(person: Person, onNavigatePerson: (Person) -> Unit) {
    Row(modifier = Modifier
        .padding(top = 32.dp)
        .clickable {
            onNavigatePerson(person)
        }
        .drawBehind {
            val y = size.height
            drawLine(
                Color.LightGray,
                Offset(250f, y),
                Offset(size.width, y),
                1f
            )
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SubcomposeAsyncImage(
            model = person.picture.medium,
            contentDescription = "person thumbnail",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(70.dp)
                .width(70.dp)
                .clip(CircleShape)
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                CircularProgressIndicator()
            } else {
                SubcomposeAsyncImageContent()
            }

        }
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .fillMaxSize(0.90f)
        ) {
            Text(
                "${person.name.first} ${person.name.last}",
                color = Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Text(
                person.email, color = Grey,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
            )
        }
        IconButton(onClick = {
            onNavigatePerson(person)
        }) {
            Icon(
                Icons.Rounded.KeyboardArrowRight,
                tint = Grey,
                contentDescription = "icon arrow see details people"
            )
        }
    }
}