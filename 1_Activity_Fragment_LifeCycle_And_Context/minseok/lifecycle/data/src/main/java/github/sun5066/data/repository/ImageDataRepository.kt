package github.sun5066.data.repository

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import github.sun5066.data.model.ImageData

class ImageDataRepository(private val context: Context) {

    companion object {
        private val CONTENT_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        private const val ID = MediaStore.Images.ImageColumns._ID
        private const val DISPLAY_NAME = MediaStore.Images.ImageColumns.DISPLAY_NAME
        private const val DATE_TOKEN = MediaStore.Images.ImageColumns.DATE_TAKEN
    }

    fun selectAll(): List<ImageData> {
        val projection = arrayOf(ID, DISPLAY_NAME, DATE_TOKEN)
        val imageList = ArrayList<ImageData>()

        context.contentResolver.query(
            CONTENT_URI, projection, null, null, "$DATE_TOKEN DESC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(ID)
            val displayNameColumn = cursor.getColumnIndexOrThrow(DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val displayName = cursor.getString(displayNameColumn)
                val contentUri = Uri.withAppendedPath(
                    CONTENT_URI, id.toString()
                )

                imageList.add(
                    ImageData(id, displayName, contentUri)
                )
            }
        }
        return imageList
    }
}