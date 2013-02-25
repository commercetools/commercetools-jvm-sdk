package de.commercetools.sphere.client.shop.model;

/** Standard image sizes available for each {@link Image}. */
public enum ImageSize {
    /** Image fits into square 50x50 pixels. */
    THUMBNAIL,
    /** Image fits into square 150x150 pixels. */
    SMALL,
    /** Image fits into square 400x400 pixels. */
    MEDIUM,
    /** Image fits into square 700x700 pixels. */
    LARGE,
    /** Image fits into square 1400x1400 pixels. */
    ZOOM,
    /** Size of the original uploaded image.
      * Note this can be smaller than some of the other sizes. */
    ORIGINAL
}
