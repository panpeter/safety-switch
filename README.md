# Safety Switch

Safety Switch is a Kotlin-based application designed to monitor a Binance Futures account and close all positions when an unrealized loss is below a certain threshold. 

It uses the Binance Futures API to fetch account information and perform operations such as closing orders and positions. It also integrates with Telegram to send notifications about the account status.

## Use cases

The Safety Switch project can be used in various scenarios, mainly related to cryptocurrency trading and risk management. Here are some possible use cases:  

- __Emergency Exit For Bots__: The Safety Switch application can serve as a safety net for a trader whose automated trading bot crashes after opening a position. If the unrealized profit falls below a critical threshold, the application triggers an emergency exit, closing all open orders and positions to prevent further losses.
- __Automated Risk Management__: Traders can also use this application to automatically manage the risk of their Binance Futures account.
- __Notification System__: The application integrates with Telegram to send notifications about the account status. This can be useful for traders who want to stay updated about their account status without constantly checking the Binance platform.

## Features

- Monitors the unrealized profit of the Binance Futures account.
- Sends a warning message via Telegram if the profit falls below a certain threshold.
- Triggers an emergency exit (closes all open orders and positions) if the profit falls below a critical threshold.
- Regularly checks the account status at a specified interval.

## Environment Variables

The application requires the following environment variables:

- `BINANCE_API_KEY`: Your Binance API key.
- `BINANCE_API_SECRET`: Your Binance API secret.
- `TELEGRAM_API_TOKEN`: Your Telegram bot API token.
- `TELEGRAM_CHAT_ID`: The chat ID where the bot will send messages.

## Running the Application

1. Clone the repository.
2. Set the required environment variables.
3. run `./gradlew installDist`.
4. run `./build/install/safetyswitch/bin/safetyswitch MainKt`.

## Contributing

Contributions are welcome. Please open an issue to discuss your ideas or submit a pull request with your changes.

## License

This project is open source and available under the [MIT License](LICENSE).

## Disclaimer

This application is for educational purposes only. Use it at your own risk. The authors are not responsible for any financial losses or damages.
