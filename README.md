### Solution to the problem:

### Database Schema

#### 1. Accounts table (ACCOUNT)

| Column Name   | Description          |
|---------------|----------------------|
| ACCOUNT_ID    | *(primary key)*      |
| CREATION_DATE |                      |

#### 2. Order table (ORDER)

| Column Name   | Description                                     |
|---------------|-------------------------------------------------|
| ORDER_ID      | *(primary key)*                                 |
| ACCOUNT_ID    | *(Foreign Key to ACCOUNT table)*                |
| ORDER_DATE    |                                                 |

#### 3. Business Owner and SubAccount Relationship (ACCOUNT_RELATIONSHIP)

| Column Name        | Description                               |
|--------------------|-------------------------------------------|
| RELATIONSHIP_ID    | *(primary key)*                           |
| BUSINESS_OWNER_ID  | *(Foreign Key to ACCOUNT table)*          |
| SUBACCOUNT_ID      | *(Foreign Key to ACCOUNT table)*          |
| RELATIONSHIP_STATUS| *(PENDING, ACCEPTED, DECLINED)*           |

### API Design

| API Endpoint                                  | Description                       |
|-----------------------------------------------|-----------------------------------|
| **/accounts/{accountId}/upgrade**             | Request to Upgrade to Business Owner |
| Method: **POST**                              |                                   |
| Request Body: None                            |                                   |
| Response: Success or failure message          |                                   |

| API Endpoint                                  | Description                       |
|-----------------------------------------------|-----------------------------------|
| **/accounts/{businessOwnerId}/invite**        | Send Invitation to Subaccount     |
| Method: **POST**                              |                                   |
| Request Body: **{ "subAccountId": "string" }**|                                   |
| Response: Success or failure message          |                                   |

| API Endpoint                                  | Description                       |
|-----------------------------------------------|-----------------------------------|
| **/accounts/{accountId}/invitations/{relationshipId}** | Accept/Decline Invitation |
| Method: **PATCH**                                      |                         |
| Request Body: **{ "status": "ACCEPTED" \| "DECLINED" }**|                         |
| Response: Success or failure message                   |                         |

| API Endpoint                                  | Description                       |
|-----------------------------------------------|-----------------------------------|
| **/accounts/{businessOwnerId}/subAccounts/{subAccountId}** | Unlink Subaccount      |
| Method: **DELETE**                                          |                        |
| Request Body: None                                          |                        |
| Response: Success or failure message                        |                        |
