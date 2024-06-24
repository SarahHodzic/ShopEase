import com.example.project2.data.ArticleDao
import com.example.project2.data.ArticleItem
import com.example.project2.data.ArticleRepository
import kotlinx.coroutines.flow.Flow

class DefaultArticleRepository(private val articleDao: ArticleDao) : ArticleRepository {
    override fun getAllItemsStream(): Flow<List<ArticleItem>> = articleDao.getAllItemsAsc()

    override fun getItemStream(id: Int): Flow<ArticleItem?> = articleDao.getItem(id)

    override fun getAllItemsAsc(): Flow<List<ArticleItem>> = articleDao.getAllItemsAsc()

    override fun getAllItemsDesc(): Flow<List<ArticleItem>> = articleDao.getAllItemsDesc()

    override suspend fun insertItem(item: ArticleItem) = articleDao.insert(item)

    override suspend fun deleteItem(item: ArticleItem) = articleDao.delete(item)

    override fun getPrice(): Flow<Double> = articleDao.getPrice()

    override suspend fun clearAllArticles() = articleDao.clearAllArticles()
}
