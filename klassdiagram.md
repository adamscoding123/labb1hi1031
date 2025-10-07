# Klassdiagram - WebShop Applikation

## Presentation-Business-Data (3-layer architecture)

```
┌────────────────────────────────────────────────────────────────────────┐
│                            PRESENTATION LAYER                          │
├────────────────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐         │
│  │ AuthController  │  │ ProductController│ │ CartController  │         │
│  │ - userService   │  │ - productService │ │ - cartService   │         │
│  ├─────────────────┤  ├─────────────────┤  ├─────────────────┤         │
│  │ + doGet()       │  │ + doGet()       │  │ + doGet()       │         │
│  │ + doPost()      │  │ + doPost()      │  │ + doPost()      │         │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘         │
│                                                                        │
│  ┌─────────────────┐  ┌──────────────────┐┌────────────────┐           │
│  │ OrderController │ │ AdminController   ││ LogoutController│          │
│  │ - orderService  │ │ - productService  ││                 │          │
│  │ - cartService   │ │ - userService     │├─────────────────┤          │
│  ├─────────────────┤  │ - categoryService││ + doGet()      │           │
│  │ + doGet()       │  ├──────────────────┤└────────────────┘           │
│  │ + doPost()      │  │ + doGet()        │                             │
│  └─────────────────┘  │ + doPost()       │                             │
│                       └──────────────────┘                             │
└────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌────────────────────────────────────────────────────────────────────────┐
│                             BUSINESS LAGER                             │
├────────────────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐         │
│  │   UserService   │  │  ProductService │  │   CartService   │         │
│  │ - userDAO       │  │ - productDAO    │  │ - cartDAO       │         │
│  ├─────────────────┤  ├─────────────────┤  │ - productService│         │
│  │ + authenticate()│  │ + getAll()      │  ├─────────────────┤         │
│  │ + getUserById() │  │ + getById()     │  │ + addToCart()   │         │
│  │ + createUser()  │  │ + create()      │  │ + getCart()     │         │
│  │ + updateUser()  │  │ + update()      │  │ + removeItem()  │         │
│  └─────────────────┘  │ + delete()      │  │ + clearCart()   │         │
│                       └─────────────────┘  └─────────────────┘         │
│                                                                        │
│  ┌─────────────────┐  ┌─────────────────┐                              │
│  │  OrderService   │  │ CategoryService │                              │
│  │ - orderDAO      │  │ - categoryDAO   │                              │
│  │ - cartService   │  ├─────────────────┤                              │
│  ├─────────────────┤  │ + getAll()      │                              │
│  │ + createOrder() │  │ + getById()     │                              │
│  │ + getOrders()   │  │ + create()      │                              │
│  │ + updateStatus()│  │ + update()      │                              │
│  └─────────────────┘  │ + delete()      │                              │
│                       └─────────────────┘                              │
└────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌──────────────────────────────────────────────────────────────────────────┐
│                              DATA LAGER                                  │
├──────────────────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐           │
│  │    UserDAO      │  │   ProductDAO    │  │    CartDAO      │           │
│  ├─────────────────┤  ├─────────────────┤  ├─────────────────┤           │
│  │ + authenticate()│  │ + getAll()      │  │ + addItem()     │           │
│  │ + getUserById() │  │ + getById()     │  │ + getCart()     │           │
│  │ + getAllUsers() │  │ + getByCategory()│ │ + removeItem()  │           │
│  │ + createUser()  │  │ + create()      │  │ + clearCart()   │           │
│  │ + updateUser()  │  │ + update()      │  │ + getItemCount()│           │
│  └─────────────────┘  │ + delete()      │  └─────────────────┘           │
│                       └─────────────────┘                                │
│                                                                          │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐           │
│  │    OrderDAO     │  │  CategoryDAO    │  │DatabaseConnection│          │
│  ├─────────────────┤  ├─────────────────┤  ├─────────────────┤           │
│  │ + createOrder() │  │ + getAll()      │  │ + getConnection()│          │
│  │ + getOrders()   │  │ + getById()     │  └─────────────────┘           │
│  │ + updateStatus()│  │ + create()      │                                │
│  │ + getOrderItems()│ │ + update()      │                                │
│  └─────────────────┘  │ + delete()      │                                │
│                       └─────────────────┘                                │
└──────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌──────────────────────────────────────────────────────────────────────────┐
│                               MODELLER                                   │
├──────────────────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐           │
│  │      User       │  │    Product      │  │    Category     │           │
│  ├─────────────────┤  ├─────────────────┤  ├─────────────────┤           │
│  │ - id            │  │ - id            │  │ - id            │           │
│  │ - username      │  │ - name          │  │ - name          │           │
│  │ - password      │  │ - description   │  │ - createdAt     │           │
│  │ - email         │  │ - price         │  │ - updatedAt     │           │
│  │ - role          │  │ - stockQuantity │  └─────────────────┘           │
│  │ - createdAt     │  │ - categoryId    │                                │
│  │ - updatedAt     │  │ - imageUrl      │                                │
│  └─────────────────┘  │ - createdAt     │                                │
│                       │ - updatedAt     │                                │
│  ┌─────────────────┐  └─────────────────┘                                │
│  │    CartItem     │                                                     │
│  ├─────────────────┤  ┌─────────────────┐  ┌─────────────────┐           │
│  │ - id            │  │     Order       │  │   OrderItem     │           │
│  │ - userId        │  ├─────────────────┤  ├─────────────────┤           │
│  │ - productId     │  │ - id            │  │ - id            │           │
│  │ - quantity      │  │ - userId        │  │ - orderId       │           │
│  │ - addedAt       │  │ - orderDate     │  │ - productId     │           │
│  └─────────────────┘  │ - totalAmount   │  │ - quantity      │           │
│                       │ - status        │  │ - unitPrice     │           │
│                       │ - shippingAddress│ └─────────────────┘           │
│                       └─────────────────┘                                │
└──────────────────────────────────────────────────────────────────────────┘

## Views (JSP-filer)
┌─────────────────────────────────────────────────────────────────────────────┐
│  login.jsp │ products.jsp │ cart.jsp │ orders.jsp │ admin.jsp │ error.jsp  │
└─────────────────────────────────────────────────────────────────────────────┘


### Arv:
- All Controller-classes → `extends HttpServlet`

### Composition:
- Controller → Service
- Service → DAO
- DAO → Model

### Usage:
- CartService → ProductService
- OrderService → CartService
- AdminController → ProductService + UserService + CategoryService

## Database connection
- All DAO-classes use `DatabaseConnection.getConnection()`
- Configuration is set in `config.properties`

## Flow
1. HTTP Request → Controller
2. Controller → Service (business logic)
3. Service → DAO (data access)
4. DAO → Database
5. Controller → JSP View
6. JSP → HTTP Response
