package org.dogadaev.lastfm.search.presentation.view.adapter

import kotlinx.android.synthetic.main.item_artist.*
import org.dogadaev.lastfm.net.data.model.getImageUrl
import org.dogadaev.lastfm.net.data.model.search.SearchArtist
import org.dogadaev.lastfm.search.R
import org.dogadaev.lastfm.statical.format.formatBigNumber
import org.dogadaev.lastfm.statical.media.ImageLoader
import org.dogadaev.lastfm.statical.widget.SimpleListAdapter
import org.koin.core.get

private typealias OnArtistClickListener = (position: Int) -> Unit

class SearchAdapter : SimpleListAdapter<SearchArtist>(SearchDiffCallback) {
    override val layoutRes: Int
        get() = R.layout.item_artist

    override val bottomReachLimit: Int
        get() = 5

    private val imageLoader: ImageLoader = get()

    private var onArtistClickListener: OnArtistClickListener? = null

    fun onArtistClicked(listener: OnArtistClickListener) {
        onArtistClickListener = listener
    }

    override fun bind(position: Int, holder: ViewHolder) {
        val context = holder.root.context
        val artist = getItem(position)
        val listeners = formatBigNumber(artist.listeners)

        holder.name.text = artist.name
        holder.listeners.text = context.getString(R.string.listeners_count, listeners)

        imageLoader.load {
            target(holder.artistCover)
            src(artist.images.getImageUrl())
            noCache()
            fallback(R.drawable.ic_no_image)
            error(R.drawable.ic_no_image)
            placeholder(R.drawable.ic_no_image)
        }

        holder.root.setOnClickListener {
            onArtistClickListener?.invoke(position)
        }
    }
}