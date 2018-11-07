/**
 * Copyright 2017 Harish Sridharan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.learnfrench.views.shimmer.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.learnfrench.views.shimmer.R;
import com.learnfrench.views.shimmer.adapters.CardAdapter;
import com.learnfrench.views.shimmer.models.ItemCard;
import com.learnfrench.views.shimmer.utils.BaseUtils;


public class ItemHolder extends RecyclerView.ViewHolder {

    private TextView mTitleView;
    private TextView mDescView;
    private ImageView mThumbnailView;
    private TextView mSummaryView;

    public static ItemHolder newInstance(ViewGroup container, int type) {
        View root = LayoutInflater.from(container.getContext()).inflate(getLayoutResourceId(type),
                container, false);

        return new ItemHolder(root);
    }

    private ItemHolder(View itemView) {
        super(itemView);

        mTitleView =  itemView.findViewById(R.id.card_title);
        mDescView =  itemView.findViewById(R.id.card_subtitle);
        mSummaryView =  itemView.findViewById(R.id.card_summary);
        mThumbnailView = itemView.findViewById(R.id.card_image);
    }

    public void bind(final ItemCard card, final CardAdapter.OnItemClickListener listener) {
        mTitleView.setText(card.getTitle());
        mThumbnailView.setImageResource(R.drawable.shopping_basket);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(card);
            }
        });
       // Glide.with(itemView.getContext()).load(card.getThumbnailUrl()).into(mThumbnailView);
    }



    private static int getLayoutResourceId(int type) {
        int selectedLayoutResource;
        switch (type) {
            case BaseUtils.TYPE_LIST:
                selectedLayoutResource = R.layout.layout_news_card;
                break;
            case BaseUtils.TYPE_SECOND_LIST:
                selectedLayoutResource = R.layout.layout_second_news_card;
                break;
            case BaseUtils.TYPE_GRID:
            case BaseUtils.TYPE_SECOND_GRID:
                selectedLayoutResource = R.layout.layout_ecom_item;
                break;
            default:
                selectedLayoutResource = 0;
        }

        return selectedLayoutResource;
    }
}
