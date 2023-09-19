package com.fuadhev.tradewave.data.repository

import android.util.Log
import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.data.local.FavoriteDAO
import com.fuadhev.tradewave.data.local.cart.CartDAO
import com.fuadhev.tradewave.data.local.cart.CartDTO
import com.fuadhev.tradewave.data.local.dto.FavoriteDTO
import com.fuadhev.tradewave.data.network.api.ProductApiService
import com.fuadhev.tradewave.data.network.dto.ProductDTO
import com.fuadhev.tradewave.data.network.dto.ProductsDTO
import com.fuadhev.tradewave.domain.model.CardUiModel
import com.fuadhev.tradewave.domain.model.CategoryUiModel
import com.fuadhev.tradewave.domain.model.OfferUiModel
import com.fuadhev.tradewave.domain.repository.ProductRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: StorageReference,
    private val productApiService: ProductApiService,
    private val favoriteDAO: FavoriteDAO,
    private val cartDAO: CartDAO,
) : ProductRepository {


    override fun getOffers(): Flow<Resource<List<OfferUiModel>>> = flow {
        emit(Resource.Loading)
        val offersList = mutableListOf<OfferUiModel>()
        val offersSnapshot = firestore.collection("offers").get().await()
        for (documentSnapshot in offersSnapshot.documents) {
            val offerUiModel = documentSnapshot.toObject(OfferUiModel::class.java)
            offerUiModel?.let {
                offersList.add(
                    OfferUiModel(
                        id = it.id,
                        title = it.title,
                        discount = it.discount,
                        thumbnailUrl = storage.child(it.thumbnailUrl).downloadUrl.await().toString(),
                        expirationDate = it.expirationDate
                    )
                )
            }
        }
        emit(Resource.Success(offersList))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Error 404"))
    }

    override fun getCategories(): Flow<Resource<List<CategoryUiModel>>> = flow {
        emit(Resource.Loading)
        val categoryList = mutableListOf<CategoryUiModel>()
        val categorySnapshot = firestore.collection("categories").get().await()


        for (documentSnapshot in categorySnapshot.documents) {
            val categoryUiModel = documentSnapshot.toObject(CategoryUiModel::class.java)
            categoryUiModel?.let {
                categoryList.add(
                    CategoryUiModel(
                        id = it.id,
                        slug = it.slug,
                        name = it.name,
                        icon = storage.child(it.icon).downloadUrl.await().toString()
                    )
                )
            }
        }
        emit(Resource.Success(categoryList))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Error 404"))
    }

    override fun getProductByCategory(category: String): Flow<Resource<ProductsDTO>> = flow {
        emit(Resource.Loading)
        val response = productApiService.getProductsByCategory(category)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Error 404"))
    }

    override fun getProducts(): Flow<Resource<ProductsDTO>> = flow {
        emit(Resource.Loading)
        val response = productApiService.getProducts()
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Error 404"))
    }

    override fun getProduct(id: Int): Flow<Resource<ProductDTO>> =flow {
        emit(Resource.Loading)
        val response = productApiService.getProduct(id)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Error 404"))
    }

    override fun getSearch(query: String): Flow<Resource<ProductsDTO>> = flow {
        emit(Resource.Loading)
        val response = productApiService.getSearch(query)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Error 404"))
    }

    override fun addFav(product: FavoriteDTO): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        favoriteDAO.addFav(product)
        emit(Resource.Success(true))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Error 404"))
    }.flowOn(Dispatchers.IO)

    override fun deleteFav(product: FavoriteDTO): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        favoriteDAO.deleteFav(product)
        emit(Resource.Success(true))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Error 404"))
    }.flowOn(Dispatchers.IO)


    override fun getFav(): Flow<Resource<List<FavoriteDTO>>> = flow {
        emit(Resource.Loading)
        val response = favoriteDAO.getFav()
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Error 404"))
    }.flowOn(Dispatchers.IO)

    override fun isProductFavorite(id: Int) : Flow<Boolean> = flow {
        val response = favoriteDAO.isProductFavorite(id)
        emit(response)
    }.flowOn(Dispatchers.IO)

    override  fun addCart(product: CartDTO) {
        cartDAO.addCart(product)
    }

    override  fun deleteCart(product: CartDTO) {
        cartDAO.deleteCart(product)
    }

    override  fun getCart(): Flow<Resource<List<CartDTO>>> = flow {
        emit(Resource.Loading)
        emit(Resource.Success(cartDAO.getCart()))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Error 404"))
    }

    override suspend fun getCards(): Flow<Resource<List<CardUiModel>>> = flow {
        emit(Resource.Loading)
        val cardSnapshot=firestore.collection("cards").document(Firebase.auth.currentUser?.uid!!).get().await()

        val snapshots=cardSnapshot.data as HashMap<*,*>
        for (snapshot in snapshots){
            val card=snapshot.value
            Log.e("card", card.toString())
        }

    }

    override fun addCard(card: CardUiModel): Flow<Resource<Boolean>> = flow{
        emit(Resource.Loading)
        val hmap= hashMapOf<String,CardUiModel>()
        hmap[card.id]=card
        firestore.collection("cards").document(Firebase.auth.currentUser?.uid!!).set(hmap,
            SetOptions.merge())

        emit(Resource.Success(true))

    }.catch {
        emit(Resource.Error(it.localizedMessage?:"Error 404"))
    }

    override fun deleteCard(card: CardUiModel): Flow<Resource<Boolean>> = flow{

    }

}
