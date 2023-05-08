# Hey! Welcome to this repo!

Market List is an API in wich you can organize your stock of wichever you want

Also, you can share the listing with your family, friends or companions
> if anyone makes a change, it will be shown to everyone

## Enpoints 
**BASE URL: ``"http://localhost:8080/api/v1"``**
#### Product (``/product``)
| endpoint | RequestParam | RequestBody | HttpMethod |
| :--- | :---: | :---: | :---: |
| / | N/A | Product |Post |
| / | id | N/A | Get |
| / | N/A | Product | Put |
| /delete | id | N/A | Delete |

#### Listing (``/listing``)
| endpoint | RequestParam | RequestBody | HttpMethod |
| :--- | :---: | :---: | :---: |
| / | Listing |N/A | Post | 
| / | id | N/A| Get | 
| / | N/A| Listing | Put | 
| /delete | id| N/A | Delete | 
| /addProduct | listingId, productId | N/A | Put |
| /removeProduct | listingId, productId | N/A | Put |

#### Group (``/group``)
| endpoint | RequestParam | RequestBody | HttpMethod |
| :--- | :---: | :---: | :---: |
| / | ownerId | Group |Post |
| / | id | N/A | Get |
| / | N/A | Group| Put |
| /delete | groupId | ownerId | Delete |
| /transfer | groupId,newOwnerId | ownerId | Put |
| /addAccount | groupId, accountId | ownerId | Put |
| /removeAccount | groupId, accountId | ownerId | Put |
| /addListing | groupId, listingId | ownerId | Put |
| /removeListing | groupId, listingId | ownerId | Put |

#### Account (``/account``)
| endpoint | RequestParam | RequestBody | HttpMethod |
| :--- | :---: | :---: | :---: |
| / | N/A | Account |Post |
| / | id or email | N/A | Get |
| / | N/A | AccountRequest | Put |
| /delete | id | N/A | Delete |

### JSON

The structure of the json is:
```
{ 
  entity: {
    attribute1,
    attribute2,
    ...
  },
  status: (HttpStatus of the response),
  message: (Message of error / success)
}
```

* Successful:
```
{
  entity: {
    description: 'Harina 0000',
    image: null,
    lastModification: '07/05/2023 - 17:35',
    measure: 'KGS',
    quantity: 1,
    preferredQuantity: 2
  },
  status: 'CREATED',
  message: 'El producto se ha creado exitosamente!'
}
```
* Not successful: 
```
{
  entity: null,
  status: 'BAD_REQUEST',
  message: 'El elemento solicitado no ha sido encontrado'
}
```


### What are you waiting for? Join to Market List!
> And if you have any questions/suggestions, feel free to contact me (links in profile)
