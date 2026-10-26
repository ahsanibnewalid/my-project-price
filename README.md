# ◆ ProjectPrice — Software Quotation Studio

A responsive, browser-only software project pricing calculator for freelancers and agencies quoting Bangladeshi and international clients.

## Features
- Project types: business website, e-commerce, SaaS, mobile, dashboards and custom software
- Editable feature-level effort estimates and complexity multipliers
- Cost-based pricing with hourly labor, direct expenses, contingency, target profit margin and discounts
- Transparent quote breakdown, estimated delivery weeks, profit and realized margin
- USD, BDT, GBP, EUR and AED with **manually editable illustrative FX rates** (not live)
- Generated project brief, price rationale and scope assumptions
- Copy proposal, print/save PDF, save estimate as JSON and browser autosave
- Fully static, mobile responsive, no backend, no API key, no data transmission

## Live website
Once GitHub Pages is enabled: https://ahsanibnewalid.github.io/my-project-price/

## Deploy
1. Open repository **Settings → Pages**.
2. Under **Build and deployment**, select **Deploy from a branch**.
3. Choose **main** and **/(root)**, then **Save**.
4. Wait for the deployment to finish and open the URL above.

## Run locally
Open `index.html` in any modern browser. No build tools required.

## Pricing method
```
hours = (base project hours + selected feature hours) × complexity multiplier + extra hours
labor = hours × hourly rate
cost = (labor + direct expenses) × (1 + contingency percentage)
pre_discount_quote = cost / (1 - target profit margin)
final_quote = pre_discount_quote × (1 - discount percentage)
realized_profit = final_quote - cost
realized_margin = realized_profit / final_quote
```
All monetary calculations use USD internally and are converted for display using the user-editable FX rate. The target profit margin is **not** a markup. Discounts can reduce the realized margin or create a loss.

### Accuracy and limitations
This tool provides explainable **estimates**, not guaranteed market prices. Starting effort assumptions are illustrative and should be calibrated against your completed projects. Currency rates are **not live**. The calculator does not automatically account for taxes, VAT, gateway/platform fees, recurring hosting, ongoing maintenance, legal terms, or customer-specific risk; add these to direct expenses where relevant. The project brief is deterministic, not AI-generated. The negotiation range is an illustrative guide, not observed market data.

## Privacy
All processing occurs in your browser. Autosave uses localStorage. Downloaded estimates remain on your device.

## License
MIT.
