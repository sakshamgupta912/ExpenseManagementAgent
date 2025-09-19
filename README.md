# ExpenseManagementAgent

A personal finance tracking Android app with automatic expense detection, categorization, and an AI agent to help organize your spending.

## Features

### ✨ Core Functionality
- **Expense Tracking**: Add, view, and manage your daily expenses
- **Automatic Categorization**: AI-powered expense categorization based on description
- **Receipt Scanning**: OCR text recognition for automatic expense detection (using ML Kit)
- **Smart Suggestions**: AI agent provides spending insights and recommendations
- **Dashboard**: Visual overview of your monthly expenses and recent transactions

### 🏗️ Technical Architecture
- **MVVM Architecture**: Clean separation of concerns with ViewModels
- **Room Database**: Local storage with LiveData for reactive UI updates
- **Material Design**: Modern UI following Google's design guidelines
- **Navigation Component**: Proper fragment navigation and deep linking
- **Repository Pattern**: Centralized data management

### 🧠 AI Features
- **Text Recognition**: Extract expense details from receipt images
- **Smart Categorization**: Automatically categorize expenses using keyword analysis
- **Spending Insights**: Personalized recommendations based on spending patterns
- **Amount Detection**: Extract monetary amounts from receipt text

## Screenshots

The app includes:
- **Dashboard**: Shows total monthly expenses and recent transactions
- **Add Expense**: Form with date picker, category selection, and AI suggestions
- **Expense List**: Comprehensive list of all expenses with filtering
- **Categories**: Manage expense categories with color coding
- **Reports**: Financial insights and spending analytics (coming soon)

## Getting Started

### Prerequisites
- Android Studio Arctic Fox (2020.3.1) or later
- Android SDK 24 (Android 7.0) or higher
- Kotlin 1.9.10

### Building the Project
1. Clone the repository
2. Open the project in Android Studio
3. Sync the project with Gradle files
4. Build and run on an emulator or device

```bash
git clone https://github.com/sakshamgupta912/ExpenseManagementAgent.git
cd ExpenseManagementAgent
./gradlew assembleDebug
```

### Dependencies
- **AndroidX Libraries**: Core, AppCompat, Material Design, Navigation
- **Room Database**: Local data persistence
- **ML Kit**: Text recognition for receipt scanning
- **CameraX**: Camera functionality for receipt capture
- **MPAndroidChart**: Data visualization for reports

## App Structure

```
app/src/main/java/com/expensemanagement/agent/
├── ai/                     # AI and ML functionality
│   └── ExpenseDetectionAgent.kt
├── data/                   # Data layer
│   ├── model/             # Data models
│   ├── dao/               # Database access objects
│   ├── database/          # Room database setup
│   └── repository/        # Repository pattern implementation
├── ui/                    # User interface
│   ├── main/              # Main activity
│   ├── dashboard/         # Dashboard fragment
│   ├── expense/           # Expense management
│   ├── category/          # Category management
│   ├── reports/           # Analytics and reports
│   └── settings/          # App settings
└── utils/                 # Utility classes
```

## Key Features Implementation

### 1. Expense Detection Agent
The AI agent (`ExpenseDetectionAgent.kt`) provides:
- OCR text extraction from receipt images
- Keyword-based expense categorization
- Amount detection from text
- Personalized spending recommendations

### 2. Database Schema
- **Expense**: id, amount, description, category, date, aiSuggestion
- **Category**: name, color, icon, monthlyBudget

### 3. UI Components
- Modern Material Design interface
- Navigation drawer with main sections
- Floating Action Button for quick expense entry
- RecyclerView with efficient list handling

## Future Enhancements

- [ ] Advanced data visualization with charts and graphs
- [ ] Budget tracking and alerts
- [ ] Export functionality (CSV, PDF)
- [ ] Cloud synchronization
- [ ] Advanced AI insights using machine learning models
- [ ] Multiple currency support
- [ ] Recurring expense tracking
- [ ] Photo attachment for receipts

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is open source and available under the [MIT License](LICENSE).

## Support

For support or questions, please open an issue in the GitHub repository.
