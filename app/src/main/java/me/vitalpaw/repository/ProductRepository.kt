package me.vitalpaw.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.vitalpaw.R
import me.vitalpaw.models.Product

class ProductRepository {

    fun getProducts(): Flow<List<Product>> {
        return flowOf(
            listOf(
                Product(
                    id = 1,
                    name = "Shampoo para perro 435 ml",
                    points = 800,
                    imageResId = R.drawable.prod7,
                    description = "Shampoo suave y sin lágrimas para perros de todas las razas."
                ),
                Product(
                    id = 2,
                    name = "Comida para perro adulto",
                    points = 2000,
                    imageResId = R.drawable.prod1,
                    description = "Alimento balanceado con proteínas y vitaminas para perros adultos."
                ),
                Product(
                    id = 3,
                    name = "Casa para gato",
                    points = 10000,
                    imageResId = R.drawable.prod2,
                    description = "Casa cómoda y acolchonada ideal para gatos pequeños y medianos."
                ),
                Product(
                    id = 4,
                    name = "Juguete para perro",
                    points = 40,
                    imageResId = R.drawable.prod3,
                    description = "Juguete resistente para morder, ideal para entrenamiento y diversión."
                ),
                Product(
                    id = 5,
                    name = "Comida para gato Don Fino 454g",
                    points = 1500,
                    imageResId = R.drawable.prod4,
                    description = "Rica en proteínas, sabor a pescado. Para gatos adultos exigentes."
                ),
                Product(
                    id = 6,
                    name = "Arena para gato",
                    points = 800,
                    imageResId = R.drawable.prod5,
                    description = "Arena absorbente y eliminadora de olores, fácil de limpiar."
                ),
                Product(
                    id = 7,
                    name = "Juguete para ave",
                    points = 65,
                    imageResId = R.drawable.prod6,
                    description = "Juguete colgante con campanas y colores vivos para aves pequeñas."
                ),
                Product(
                    id = 8,
                    name = "Juguete para gato",
                    points = 80,
                    imageResId = R.drawable.prod8,
                    description = "Pelota interactiva con cascabel para mantener entretenido a tu gato."
                )
            )
        )
    }
}
