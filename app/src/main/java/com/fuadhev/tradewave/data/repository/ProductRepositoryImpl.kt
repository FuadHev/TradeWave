package com.fuadhev.tradewave.data.repository

import com.fuadhev.tradewave.common.utils.Resource
import com.fuadhev.tradewave.data.network.api.ProductApiService
import com.fuadhev.tradewave.data.network.dto.ProductDTO
import com.fuadhev.tradewave.data.network.dto.ProductsDTO
import com.fuadhev.tradewave.domain.model.CategoryUiModel
import com.fuadhev.tradewave.domain.model.OfferUiModel
import com.fuadhev.tradewave.domain.repository.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: StorageReference,
    private val productApiService: ProductApiService,
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


}
