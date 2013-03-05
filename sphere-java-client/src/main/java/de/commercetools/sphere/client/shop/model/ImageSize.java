package de.commercetools.sphere.client.shop.model;

/** Standard image sizes available for each {@link Image}. */
public enum ImageSize {
    /** Image fits into square 50x50 pixels, maintaining aspect ratio. */
    THUMBNAIL,
    /** Image fits into square 150x150 pixels, maintaining aspect ratio. */
    SMALL,
    /** Image fits into square 400x400 pixels, maintaining aspect ratio. */
    MEDIUM,
    /** Image fits into square 700x700 pixels, maintaining aspect ratio. */
    LARGE,
    /** Image fits into square 1400x1400 pixels, maintaining aspect ratio. */
    ZOOM,
    /** Original uploaded size.
      * Note this can be smaller than some of the other sizes. */
    ORIGINAL
}
