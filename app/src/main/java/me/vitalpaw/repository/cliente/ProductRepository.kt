package me.vitalpaw.repository.cliente

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
                    points = 80,
                    imageResId = R.drawable.prod7,
                    description = "Shampoo suave y sin lágrimas para perros de todas las razas."
                ),
                Product(
                    id = 2,
                    name = "Comida para perro adulto",
                    points = 500,
                    imageResId = R.drawable.prod1,
                    description = "Alimento balanceado con proteínas y vitaminas para perros adultos."
                ),
                Product(
                    id = 3,
                    name = "Casa para gato",
                    points = 5000,
                    imageResId = R.drawable.prod2,
                    description = "Casa cómoda y acolchonada ideal para gatos pequeños y medianos."
                ),
                Product(
                    id = 4,
                    name = "Juguete para perro",
                    points = 20,
                    imageResId = R.drawable.prod3,
                    description = "Juguete resistente para morder, ideal para entrenamiento y diversión."
                ),
                Product(
                    id = 5,
                    name = "Comida para gato Don Fino 454g",
                    points = 400,
                    imageResId = R.drawable.prod4,
                    description = "Rica en proteínas, sabor a pescado. Para gatos adultos exigentes."
                ),
                Product(
                    id = 6,
                    name = "Arena para gato",
                    points = 35,
                    imageResId = R.drawable.prod5,
                    description = "Arena absorbente y eliminadora de olores, fácil de limpiar."
                ),
                Product(
                    id = 7,
                    name = "Juguete para ave",
                    points = 10,
                    imageResId = R.drawable.prod6,
                    description = "Juguete colgante con campanas y colores vivos para aves pequeñas."
                ),
                Product(
                    id = 8,
                    name = "Juguete para gato",
                    points = 20,
                    imageResId = R.drawable.prod8,
                    description = "Pelota interactiva con cascabel para mantener entretenido a tu gato."
                ),
                Product(
                    id = 9,
                name = "Snacks dentales para perro",
                points = 40,
                imageResId = R.drawable.prod9,
                description = "Premios que ayudan a mantener los dientes limpios y el aliento fresco."
                        ),
                Product(
                id = 10,
                name = "Collar antipulgas",
                points = 200,
                imageResId = R.drawable.prod10,
                description = "Collar repelente de pulgas y garrapatas con duración de hasta 8 meses."
                ),
                Product(
                id = 11,
                name = "Bebedero automático",
                points = 600,
                imageResId = R.drawable.prod11,
                description = "Fuente de agua continua para mantener hidratada a tu mascota."
                ),
                Product(
                id = 12,
                name = "Rascador para gato",
                points = 350,
                imageResId = R.drawable.prod12,
                description = "Rascador de cartón ecológico con diseño atractivo para tu gato."
                ),
                Product(
                id = 13,
                name = "Transportadora pequeña",
                points = 700,
                imageResId = R.drawable.prod13,
                description = "Ideal para viajes o visitas al veterinario de mascotas pequeñas."
                ),
                Product(
                id = 14,
                name = "Pelota interactiva con luces",
                points = 48,
                imageResId = R.drawable.prod14,
                description = "Pelota con luces LED y sensores de movimiento para juego nocturno."
                ),
                Product(
                id = 15,
                name = "Correa retráctil",
                points = 375,
                imageResId = R.drawable.prod15,
                description = "Correa de 5 metros con botón de freno para paseos seguros."
                ),
                Product(
                id = 16,
                name = "Ropa para perro talla S",
                points = 65,
                imageResId = R.drawable.prod16,
                description = "Ropa cómoda y suave ideal para razas pequeñas."
                ),
                Product(
                id = 17,
                name = "Vitaminas para cachorros",
                points = 150,
                imageResId = R.drawable.prod17,
                description = "Suplemento en tabletas para el bienestar, pelaje y salud de tus mascotas."
                ),
                Product(
                id = 18,
                name = "Snack para roedores",
                points = 80,
                imageResId = R.drawable.prod18,
                description = "Bocados nutritivos con sabor a frutas para hámsters y conejillos."
                ),
                Product(
                id = 19,
                name = "Jaula para pájaros",
                points = 800,
                imageResId = R.drawable.prod19,
                description = "Jaula espaciosa y segura con perchas y comederos incluidos."
                ),
                Product(
                id = 20,
                name = "Kit de higiene para gato",
                points = 22,
                imageResId = R.drawable.prod20,
                description = "Incluye cepillo, pasta y guantes para el cuidado diario."
                ),
                Product(
                id = 21,
                name = "Champú medicado para piel sensible",
                points = 190,
                imageResId = R.drawable.prod21,
                description = "Champú hipoalergénico ideal para pieles sensibles o alérgicas."
                ),
                Product(
                id = 22,
                name = "Bolsas biodegradables para heces",
                points = 25,
                imageResId = R.drawable.prod22,
                description = "Rollos de bolsas ecológicas para recoger los desechos."
                ),
                Product(
                id = 23,
                name = "Arnés ajustable para perro",
                points = 300,
                    imageResId = R.drawable.prod23,
                description = "Cómodo y seguro, disponible en varios tamaños y colores."
                ),
                Product(
                id = 24,
                name = "Ropa para gato",
                points = 90,
                imageResId = R.drawable.prod24,
                description = "Camisa ligero para gatos que no les gusta el frío."
                ),
                Product(
                id = 25,
                name = "Túnel para gatos",
                points = 400,
                imageResId = R.drawable.prod25,
                description = "Túnel plegable ideal para jugar y esconderse."
                ),
                Product(
                id = 26,
                name = "Comedero doble de acero inoxidable",
                points = 150,
                imageResId = R.drawable.prod26,
                description = "Resistente y fácil de limpiar, para comida y agua."
                ),
                Product(
                id = 27,
                name = "Kit dental para mascotas",
                points = 22,
                imageResId = R.drawable.prod27,
                description = "Incluye cepillo, pasta dental y dedal de limpieza."
                ),
                Product(
                id = 28,
                name = "Alfombra refrigerante para verano",
                points = 990,
                imageResId = R.drawable.prod28,
                description = "Alfombra con gel refrescante para mantener a tu mascota fresca."
                )
            )
        )

    }
}