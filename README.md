# BeerComposeApp

## Introduction

This is my first project, an app for a bar.

## Technologies used in this project

- Language: Kotlin
- Jetpack Compose
- Coroutines
- Room
- Retrofit
- Hilt
- DataStore

## Content

In my app, users can choose a category of products to see the menu. On the menu screen, users will see products of the selected category, images of products, prices, and volume for drinks or weight for snacks. By clicking on the product, the user will be taken to the screen of this product to see expanded information about it. Users can add products to the shopping cart and make an order. Additionally, users can register and then authorize using a telephone number and password (not an actual number; any numbers would work as long as they are not used by another user). Registration would allow users to like products, which are assigned to the user. On the profile screen, users can change their login or password. To navigate through the app, users can use the bottom bar or swipes.

## How it works

On the first launch, products will be downloaded from the server kindly provided by my friend. The server needs time to start, so the first attempts to launch the app may result in an error, but a button to try again is provided. Finally, when data is loaded, it is saved in the database and then loaded to show on screen. On subsequent launches, the app will check if the database is empty and decide whether to make a server request. However, images are loaded every time because it is too expensive to save them. Users' information, their likes, and cart content are also saved in their tables. The database consists of four tables: Menu items, users, cart, and a join table of products and user - likes. When logged in, the user is saved to DataStore, so there is no need to repeat this on every launch. When creating this app, I was trying to stick to the Clean Architecture design pattern, so most of the logic is placed in use cases. To set up navigation with swipes, HorizontalPager was used.
